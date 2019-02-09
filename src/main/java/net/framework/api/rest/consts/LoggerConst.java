package net.framework.api.rest.consts;

/**
 * ロガークラス用定数クラス。
 */
public class LoggerConst {

	//------------------------------------
    // ログレベル
    //------------------------------------
    /**
     * ログレベル、エラー
     */
    public static final String LOG_LEVEL_ERROR = "ERROR";

    /**
     * ログレベル、警告
     */
    public static final String LOG_LEVEL_WARN = "WARN";

    /**
     * ログレベル、インフォ
     */
    public static final String LOG_LEVEL_INFO = "INFO";

    /**
     * ログレベル、トレース
     */
    public static final String LOG_LEVEL_TRACE = "TRACE";


    //------------------------------------
    // ログプレフィックス用レベル別定数
    //------------------------------------
    /**
     * マーカー、エラー
     */
    public static final String LOG_PREFIX_ERROR = "ERROR";

    /**
     * マーカー、警告
     */
    public static final String LOG_PREFIX_WARN = "WARN ";

    /**
     * マーカー、インフォ
     */
    public static final String LOG_PREFIX_INFO = "INFO ";

    /**
     * マーカー、トレース
     */
    public static final String LOG_PREFIX_TRACE = "     ";


    //------------------------------------
    // MDCキー定数
    //------------------------------------

    /**
     * クラス名
     */
    public static final String CLASS_NAME = "className";

    /**
     * メソッド名
     */
    public static final String METHOD_NAME = "methodName";

    /**
     * コード値
     */
    public static final String LOG_CODE = "logCode";

    /**
     * メッセージ
     */
    public static final String LOG_MESSAGE = "logMessage";

    /**
     * JSON出力内容
     */
    public static final String JSON_BODY = "jsonBody";

    /**
     * エラースタックトレース
     */
    public static final String ERR_STACK_TRACE = "errStackTrace";


    //------------------------------------
    // 出力文字列定数
    //------------------------------------

    /**
     * 電文ログ、開始
     */
    public static final String STACKTRACE_START = "[Telegram Trace Start]";

    /**
     * 電文ログ、終了
     */
    public static final String STACKTRACE_END = "[Telegram Trace End]";

    /**
     * 改行コード
     */
    public static final String STR_NEWLINE = "\n";

}
