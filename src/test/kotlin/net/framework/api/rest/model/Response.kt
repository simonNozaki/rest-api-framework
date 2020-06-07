package net.framework.api.rest.model

/**
 * テスト用ダミーAPIレスポンス
 */
data class Response (
    var orderId: String,
    var merchantId: String,
    var errors: Errors?
)