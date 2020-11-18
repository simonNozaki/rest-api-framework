package net.framework.api.rest.helper;

import net.framework.api.rest.model.Errors;
import net.framework.api.rest.model.ServiceOut;
import net.framework.api.rest.config.AppLogger;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Controller helper class
 */
public class RestControllerHelper {

    /**
     * Return ResponseProcessor
     * @param <T> type parameter for value
     * @return ResponseProcessor
     */
    protected static <T> ResponseProcessor<T> responseProcessBuilder() {
        return new ResponseProcessor<>();
    }

    /**
     * Response singleton
     * concrete class for building API response
     * @param <T> type parameter for value of singleton
     */
    protected static final class ResponseProcessor<T> {

        private final T value;

        private Errors errors;

        /**
         * nullを許容するデフォルトコンストラクタ.
         */
        ResponseProcessor() {
            this.value = null;
        }

        /**
         * 引数をもらった場合のデフォルトコンストラクタ.
         * @param value 任意の型の値
         */
        private ResponseProcessor(T value) {
            this.value = Objects.requireNonNull(value);
        }

        /**
         * 引数をもらった場合のデフォルトコンストラクタ。エラー情報も同時に引き受けます。
         * @param value 任意の型の値
         * @param e an error
         */
        private ResponseProcessor(T value, Errors e) {
            this.value = Objects.requireNonNull(value);
            this.errors = e;
        }

        /**
         * プロセッサを開始します.
         * @param supplier レスポンスオブジェクトのサプライヤ
         * @param <R> サプライヤのジェネリクス
         * @return ResponseProcessor ローカルな自分のクラス
         */
        public <R> ResponseProcessor<R> of(Supplier<R> supplier) {
            return new ResponseProcessor<>(supplier.get());
        }

        /**
         * プロセッサを開始します。具体的な値でコンストラクタを呼び出します。
         * @param input 任意の入力値
         * @param <R> 入力値のジェネリクス
         * @return ResponseProcessor ローカルな自分のクラス
         */
        public <R> ResponseProcessor<R> with(R input) {
            return new ResponseProcessor<>(input);
        }

        /**
         * サービスクラスを実行します。正常、異常結果をそれぞれローカル変数に格納してパイプラインを継続します。<br>
         * これは中間操作です。
         * @param out サービスクラスの実行結果
         * @param <R> サービスアウトのジェネリクス
         * @return ResponseProcessor result inputted processor
         */
        public <R> ResponseProcessor<R> executeService(ServiceOut<R> out) {
            Optional.ofNullable(out.getErrors()).ifPresent((Errors serviceOutErrors) -> {
                this.errors.getCodes().addAll(serviceOutErrors.getCodes());
            });
            return new ResponseProcessor<>(out.getValue(), out.getErrors());
        }

        /**
         * 生成したDTOに対する中間操作を提供します.
         * @param function a mapping function
         * @param <R> ラムダ関数の戻り値のジェネリクス
         * @return a ResponseProcessor instance
         */
        public <R> ResponseProcessor<R> map(Function<T, R> function) {
            return new ResponseProcessor<>(function.apply(value));
        }

        /**
         * サービスクラスの結果を別のオブジェクトにマップします。
         * @param bifunction a mapping function with a value and an error
         * @param <R> generics of a value
         * @param <U> generics
         * @return a ResponseProcessor instance
         */
        public <U, R> ResponseProcessor<T> mapWithError(BiFunction<T, Errors, R> bifunction) {
            return new ResponseProcessor(bifunction.apply(value, errors));
        }

        /**
         * 事前の操作の適正性を評価して、正しければそのまま値を返します.
         * @param predicate a condition
         * @return ResponseProcessor
         */
        public ResponseProcessor<T> test(Predicate<T> predicate) {
            if (predicate.test(value)) {
                return new ResponseProcessor<>(value);
            }
            return new ResponseProcessor<>();
        }

        /**
         * Offer manual logging function.
         * intermediate manipulation.
         * @param code a log code, nullable
         * @param message a log message, nullable
         * @param input a logged object
         * @param <V> generics of an input
         * @return ResponseProcessor
         * @throws IOException IO exception
         */
        public <V> ResponseProcessor<T> logOutput(String code, String message, V input) throws IOException {
            AppLogger.traceTelegram(
                    code, message, this.getClass(), new Object(){}.getClass().getEnclosingMethod().getName(), MappingSingleton.Companion.getMapper().toJson(this.value)
            );
            return new ResponseProcessor<>(value);
        }

        /**
         * ログ出力機能を提供します。サービスクラス実行後に利用することで、引数なしで電文ログ出力ができます。<br>
         * サービス実行前にこのメソッドを実行しても、何も出力されません。
         * @param code a log code, nullable
         * @param message a log message, nullable
         * @return ResponseProcessor ローカルなResponseProcessorのインスタンスを返却します。
         * @throws IOException IO exception
         */
        public ResponseProcessor<T> log(String code, String message) throws IOException {
            AppLogger.traceTelegram(
                    code, message, this.getClass(), new Object(){}.getClass().getEnclosingMethod().getName(), MappingSingleton.Companion.getMapper().toJson(this.value)
            );
            return new ResponseProcessor<>(value);
        }

        /**
         * 操作を実行したパイプラインからレスポンスインスタンスを取得します。これは終端操作です。
         * @return a result object
         */
        public T apply() {
            return this.value;
        }
    }

}
