package net.framework.api.rest.exception

import java.lang.RuntimeException

/**
 * Web APIクライアントのBadRequest用実行時例外クラス。
 */
class SimpleApiBadRequestException: RuntimeException {

    /**
     * デフォルトコンストラクタ、メッセージあり
     */
    constructor(message: String): super(message){
    }

    /**
     * デフォルトコンストラクタ、メッセージ/原因あり
     */
    constructor(message: String, cause: Throwable): super(message, cause){
    }
}