package net.framework.api.rest.model

/**
 * 正常なOrderインスタンスを一件返却します
 */
fun createNormalRequest(orderId: String, merchantId: String): Order =
    Order(
        orderId = orderId,
        merchantId = merchantId,
        items = mutableListOf(
            Item("merchant001", "hat", 1000, 1, Customer("customer001", "Patrick Collison", 0, 31)),
            Item("merchant001", "bag", 2000, 1, Customer("customer001", "Patrick Collison", 0, 31))
        )
    )

/**
 * 正常なResponseインスタンスを一つ返却します
 * @return
 */
fun createNormalResponse(orderId: String, merchantId: String): Response =
    Response(
        orderId = orderId,
        merchantId = merchantId,
        errors = null
    )