package net.framework.api.rest.config;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import net.framework.api.rest.config.logger.AppWarnLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import ch.qos.logback.classic.Level;
import net.framework.api.rest.config.logger.AppErrorLogger;
import net.framework.api.rest.config.logger.AppTelegramLogger;
import net.framework.api.rest.config.logger.AppTraceLogger;
import net.framework.api.rest.constant.LoggerConst;


/**
 * 共通ログ出力設定クラスです.
 */
public class AppLogger {

	private static final Logger appTraceINSTANCE = LoggerFactory.getLogger(AppTraceLogger.class.getCanonicalName());

	private static final Logger appTelegramINSTANCE = LoggerFactory.getLogger(AppTelegramLogger.class.getCanonicalName());

	private static final Logger appWarnINSTANCE = LoggerFactory.getLogger(AppWarnLogger.class.getCanonicalName());

	private static final Logger appErrorINSTANCE = LoggerFactory.getLogger(AppErrorLogger.class.getCanonicalName());

	/**
	 * ログレベルに応じたログ出力を実施します。<br>
	 * bodyを指定せず、nullが設定された場合MDCのキーを設定しません。
	 * @param level
	 * @param message
	 * @param th
	 * @param className
     * @param methodName
	 */
	private static void log(String level, String code, String message, Throwable th, Object className, Object methodName) {
	    // MDCを初期化
	    MDC.put(LoggerConst.CLASS_NAME, className.toString());
	    MDC.put(LoggerConst.METHOD_NAME, methodName.toString());
	    MDC.put(LoggerConst.LOG_CODE, code);
	    MDC.put(LoggerConst.LOG_MESSAGE, message);
	    if (th != null) {
	        MDC.put(LoggerConst.ERR_STACK_TRACE, getStackTraceString(th));
	    }

	    // マーカーを初期化
	    Marker logPrefix;

	    // 例外を受け付けられる形に変換する
	    Optional<Throwable> exception = Optional.ofNullable(th);

	    // ログレベルをロガーに入力
	    ch.qos.logback.classic.Logger loggerInstance = (ch.qos.logback.classic.Logger)appTraceINSTANCE;
		ch.qos.logback.classic.Logger errorLoggerInstance = (ch.qos.logback.classic.Logger)appErrorINSTANCE;
		ch.qos.logback.classic.Logger warnLoggerInstance = (ch.qos.logback.classic.Logger)appWarnINSTANCE;


		// レベル別にログを出力
	    switch(level) {
	        case LoggerConst.LOG_LEVEL_ERROR:
				errorLoggerInstance.setLevel(Level.ERROR);
	            logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_ERROR);
				appErrorINSTANCE.error(logPrefix, code, exception);
	            break;
	        case LoggerConst.LOG_LEVEL_WARN:
				warnLoggerInstance.setLevel(Level.WARN);
	            logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_WARN);
	            appTraceINSTANCE.warn(logPrefix, code, exception);
                break;
	        case LoggerConst.LOG_LEVEL_INFO:
                loggerInstance.setLevel(Level.INFO);
	            logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_INFO);
	            appTraceINSTANCE.info(logPrefix, code, exception);
	            break;
	        case LoggerConst.LOG_LEVEL_TRACE:
	            loggerInstance.setLevel(Level.TRACE);
	            logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_TRACE);
	            appTraceINSTANCE.trace(logPrefix, code, exception);
                break;
	        default:
	            break;
	    }
	    // MDCのクリア
	    clearLocalMDC();
	}

	/**
	 * 電文ログの出力実施を提供します。
	 * @param level
	 * @param code
	 * @param message
	 * @param className
	 * @param methodName
	 * @param body
	 */
	private static void logTelegram(String level, String code, String message, Object className, Object methodName, String body) {
	    // MDCを初期化
        MDC.put(LoggerConst.CLASS_NAME, className.toString());
        MDC.put(LoggerConst.METHOD_NAME, methodName.toString());
        MDC.put(LoggerConst.LOG_CODE, code);
        MDC.put(LoggerConst.LOG_MESSAGE, message);
        MDC.put(LoggerConst.JSON_BODY, getStackTraceString(body));

        // マーカーを初期化
        Marker logPrefix;

        // ログレベルをロガーに入力
        ch.qos.logback.classic.Logger loggerInstance = (ch.qos.logback.classic.Logger)appTelegramINSTANCE;
		ch.qos.logback.classic.Logger errorLoggerInstance = (ch.qos.logback.classic.Logger)appErrorINSTANCE;
		ch.qos.logback.classic.Logger warnLoggerInstance = (ch.qos.logback.classic.Logger)appWarnINSTANCE;

        // レベル別にログを出力
        switch(level) {
            case LoggerConst.LOG_LEVEL_ERROR:
				errorLoggerInstance.setLevel(Level.ERROR);
                logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_ERROR);
				appErrorINSTANCE.error(logPrefix, code);
                break;
            case LoggerConst.LOG_LEVEL_WARN:
				warnLoggerInstance.setLevel(Level.WARN);
                logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_WARN);
                appTelegramINSTANCE.warn(logPrefix, code);
                break;
            case LoggerConst.LOG_LEVEL_INFO:
                loggerInstance.setLevel(Level.INFO);
                logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_INFO);
                appTelegramINSTANCE.info(logPrefix, code);
                break;
            case LoggerConst.LOG_LEVEL_TRACE:
                loggerInstance.setLevel(Level.TRACE);
                logPrefix = MarkerFactory.getMarker(LoggerConst.LOG_PREFIX_TRACE);
                appTelegramINSTANCE.trace(logPrefix, code);
                break;
            default:
                break;
        }
        // MDCのクリア
        clearLocalMDC();

	}

	/**
	 * トレースログを出力します。
	 * @param code
	 * @param message
	 * @param th
	 * @param className
	 * @param methodName
	 */
	public static void trace(String code, String message, Throwable th, Object className, Object methodName) {
	    log(LoggerConst.LOG_LEVEL_INFO, code, message, th, className, methodName);
	}

	/**
	 * エラーログを出力します。
	 * @param code
	 * @param message
     * @param th
     * @param className
     * @param methodName
	 */
	public static void error(String code, String message, Throwable th, Object className, Object methodName) {
	    log(LoggerConst.LOG_LEVEL_ERROR, code, message, th, className, methodName);
	}

	/**
	 * 警告ログを出力します。
	 * @param code
	 * @param message
	 * @param th
	 * @param className
	 * @param methodName
	 */
	public static void warn(String code, String message, Throwable th, Object className, Object methodName) {
		log(LoggerConst.LOG_LEVEL_WARN, code, message, th, className, methodName);
	}

	/**
	 * 電文ログを出力します。
	 * @param code
	 * @param message
	 * @param className
	 * @param methodName
	 * @param body
	 */
	public static void traceTelegram(String code, String message, Object className, Object methodName, String body) {
	    logTelegram(LoggerConst.LOG_LEVEL_INFO, code, message, className, methodName, body);
	}

    /**
	 * スレッドローカルなMDCのキー情報をクリアします。
	 */
	private static void clearLocalMDC() {
	    MDC.remove(LoggerConst.CLASS_NAME);
	    MDC.remove(LoggerConst.METHOD_NAME);
	    MDC.remove(LoggerConst.LOG_CODE);
	    MDC.remove(LoggerConst.LOG_MESSAGE);
	    if (MDC.get(LoggerConst.ERR_STACK_TRACE) != null) {
	        MDC.remove(LoggerConst.ERR_STACK_TRACE);
	    }
	}

	/**
	 * [例外クラス用]スタックトレース書き出し文字列を作成します。
	 * @param excp
	 * @return 例外出力
	 */
	private static String getStackTraceString(Throwable excp) {
        StringBuilder sb = new StringBuilder();
        sb.append(LoggerConst.STR_NEWLINE);
        sb.append(LoggerConst.EXCEPTION_START);
        sb.append(LoggerConst.STR_NEWLINE);
        StringWriter stringWriter = new StringWriter();
        excp.printStackTrace(new PrintWriter(stringWriter));
        sb.append(stringWriter.toString());
        sb.append(LoggerConst.STR_NEWLINE);
        sb.append(LoggerConst.EXCEPTION_END);

        return sb.toString();
    }

	/**
     * <p>
	 * [普通の文字列用]スタックトレース書き出し文字列を作成します。<br>
     * 例：<br>
     * [Handled Exception Start]<br>
     * 電文ログ本文<br>
     * [Handled Exception End]<br>
	 * </p>
     * @param body
     * @return トレースログ出力
     */
    private static String getStackTraceString(String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(LoggerConst.STR_NEWLINE);
        sb.append(LoggerConst.TELEGRAM_START);
        sb.append(LoggerConst.STR_NEWLINE);
        sb.append(body);
        sb.append(LoggerConst.STR_NEWLINE);
        sb.append(LoggerConst.TELEGRAM_END);

        return sb.toString();
    }

}
