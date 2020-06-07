package net.framework.api.rest.model

data class Order (
        var orderId: String,
        var merchantId: String,
        var items: List<Item>
)