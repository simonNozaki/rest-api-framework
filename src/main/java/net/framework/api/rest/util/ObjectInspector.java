package net.framework.api.rest.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.framework.api.rest.model.Errors;
import net.framework.api.rest.config.AppLogger;
import net.framework.api.rest.constant.AppConst;

/**
 * This class provides the inspection functions for objects.
 */
public class ObjectInspector {

    // Forbid a default constructor
    private ObjectInspector() {}

    /**
     * インプットの入力検査パイプラインを開始します.入力チェック操作はInspectorクラスが提供します.
     * @param T input
     * @return InputInspector<T>
     */
    public static <T> Inspector<T> of(T input) {
        return new Inspector<T>(input);
    }

    /**
     * 入力検査機能を提供するクラスです.
     * @param <T>
     */
    public static final class Inspector<T> {

        T value;

        Errors errors;

        /**
         * デフォルトコンストラクタ
         * @param value コンストラクタ引数
         */
        private Inspector(T value) {
            this.value = value;
            this.errors = new Errors();
        }

        /**
         * デフォルトコンストラクタ
         * @param value コンストラクタ引数
         * @param errors エラー引数
         */
        private Inspector(T value, Errors errors) {
            this.value = value;
            if (ObjectUtil.isNullOrEmpty(this.errors)) {
                this.errors = new Errors();
            }
            this.errors = errors;
        }

        /**
         * {@code of}メソッドで初期化した検査対象をJSON形式でログ出力します。
         * JSONシリアライズに失敗等システムエラーが発生した場合、パイプラインを継続しつつログ出力します。
         * @param code ログに出すコード
         * @param message ログメッセージ
         * @return {@code Inspector}
         * @throws Exception
         */
        public <V> Inspector<T> log(String code, String message){
            try{
                AppLogger.traceTelegram(code, message, this.getClass(), new Object(){}.getClass().getEnclosingMethod().getName(), new ObjectMapper().writeValueAsString(this.value));
                return new Inspector<T>(value);
            }catch(Exception e){
                AppLogger.error(null, AppConst.SYSTEM_ERROR, e, this.getClass(), new Object(){}.getClass().getEnclosingMethod().getName());
                return new Inspector<T>(value);
            }
        }

        /**
         * 入力がnullもしくは空の場合、エラーコードを設定します.
         * @param value
         * @return Inspector<T>
         * @throws Exception
         */
        public Inspector<T> hasNullValue(String code){
            return this.satisfyPredicateWithInput(this.value, (T inputValue) -> ObjectUtil.isNullOrEmpty(this.value), code);
        }

        /**
         * プロパティの空チェック。空文字もしくはnullの場合エラーコードを設定します。
         * @param subject 被検査対象
         * @param code エラーコード
         * @return Inspector
         */
        public <V> Inspector<T> isNull(V subject, String code){
            return this.satisfyPredicateWithInput(subject, (V inputValue) -> ObjectUtil.isNullOrEmpty(subject), code);
        }

        /**
         * 桁数チェックを行い、上限を超えていればエラーコードを設定します。
         * @param String target
         * @param int max
         * @param String code
         * @return
         */
        public <V> Inspector<T> violateMaxLength(V target, int max, String code) {
            if (ObjectUtil.isNullOrEmpty(target)) {
                return new Inspector<T>(this.value, this.errors);
            }
            return this.satisfyPredicateWithInput(target, (V inputValue) -> StringUtil.isOverSpecificLength(target.toString(), max), code);
        }

        /**
         * 桁数チェックを行い、指定された文字長でなければエラーコードを設定します。
         * @param String target
         * @param int max
         * @param String code
         * @return
         */
        public <V> Inspector<T> violateSpecificLength(V target, int length, String code) {
            // 空文字、もしくはnullの場合強制的にエラー設定
            if (ObjectUtil.isNullOrEmpty(target)) {
                return this.satisfyPredicateWithInput(target, (V value) -> true, code);
            }
            return this.satisfyPredicateWithInput(target, (V inputValue) -> !StringUtil.isParticularLength(target, length), code);
        }

        /**
         * 述語を評価します。
         * @param predicate 述語
         * @param code エラーコード
         * @return Inspectorインスタンス
         */
        public Inspector<T> test(Predicate<T> predicate, String code) {
            return this.satisfyPredicateWithInput(this.value, predicate, code);
        }

        /**
         * Evaluate a predicate for an iterable.
         * @param target
         * @param code
         * @param <E>
         * @return
         */
        public <E> Inspector<T> testFromIterable(Collection<E> target, Predicate<E> predicate, String code) {
            return this.satisfyPredicateWithIterable(target, predicate, code);
        }

        /**
         * This method ensure that a target satisfy a specific condition.
         * @param target target object
         * @param predicate predicate
         * @param code an error code
         * @param <E> type parameter
         * @return an Inspector instance
         */
        private <E> Inspector<T> satisfyPredicateWithIterable(Collection<E> target, Predicate<E> predicate, String code) {
            long counter = ObjectUtil.getStream(target)
                    .filter(predicate::test)
                    .count();
            if (counter > 0) {
                // エラーコードのリストがない場合はリストを初期化する
                List<String> codes = Optional.ofNullable(this.errors.getCodes()).orElse(new ArrayList<>());
                codes.add(code);
                this.errors.setCodes(codes);
            }
            return new Inspector(this.value, this.errors);
        }

        /**
         * 入力が指定された条件を満たすことを確認します.<br>
         * 条件にマッチするエラーを入力することで、エラーコードを設定します.
         * @param input 評価対象
         * @param predicate 述語
         * @param code エラーコード
         * @return Inspectorインスタンス
         */
        private <V> Inspector<T> satisfyPredicateWithInput(V input, Predicate<V> predicate, String code) {
            if (predicate.test(input)) {
                // エラーコードのリストがない場合はリストを初期化する
                List<String> codes = Optional.ofNullable(this.errors.getCodes()).orElse(new ArrayList<>());
                codes.add(code);
                this.errors.setCodes(codes);
            }
            return new Inspector(this.value, this.errors);
        }

        /**
         * エラーを昇順に構築します。
         * @return Errors エラー情報
         * @throws Exception
         */
        public Errors build(){
            if (!ObjectUtil.isNullOrEmpty(this.errors)) {
                List<String> sortedCodes = ObjectUtil.getStream(this.errors.getCodes())
                        .sorted(Comparator.comparing((String code) -> code))
                        .collect(Collectors.toList());
                this.errors = new Errors(sortedCodes);
            }
            return this.errors;
        }
    }

}
