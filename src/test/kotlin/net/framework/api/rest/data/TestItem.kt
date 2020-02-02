package net.framework.api.rest.data

/**
 * テスト用Itemデータクラス。
 */
data class TestItem (
        var id: String?,
        var name: String?,
        var amount: Int,
        var quantity: Int,
        var customer: TestCustomer?
)