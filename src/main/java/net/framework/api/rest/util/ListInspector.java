package net.framework.api.rest.util;

import java.util.List;

import net.framework.api.rest.Errors;
import net.framework.api.rest.util.ObjectInspector.Inspector;

/**
 * リストオブジェクトの入力検査クラス。
 */
public class ListInspector<T> {

    private List<T> subject;

    private ListInspector(List<T> subject) {
        this.subject = subject;
    }

    public static <T> ListInspector<T> listOf(List<T> subject){
        return new ListInspector<T>(subject);
    }

    /**
     * 条件およびエラーメッセージを設定した検査クラスを引き受け、評価パイプラインを開始します。
     * @param {@code inspector} オブジェクト検査インスタンス
     * @return {@code InspectionExecutor} 検査実行クラス
     */
    public static <T> InspectionExecutor<T> conditionWith(Inspector<T> inspector){
        return new InspectionExecutor<T>(inspector);
    }

    /**
     * リスト検査の実行クラス
     * @param <T>
     */
    public static final class InspectionExecutor<T>{

        private Inspector inspector;
        private List<Errors> errorsList;

        /**
         * コンストラクタ、不可視。
         */
        private InspectionExecutor() {}

        /**
         * デフォルトコンストラクタ。引数あり
         * @param inspector
         */
        public InspectionExecutor(Inspector inspector) {
            this.inspector = inspector;
        }

    }


}
