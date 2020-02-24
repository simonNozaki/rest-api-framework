package net.framework.api.rest.data

data class Order (
        var orderId: String,
        var merchantId: String,
        var items: List<TestItem>
)