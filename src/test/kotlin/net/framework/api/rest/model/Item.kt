package net.framework.api.rest.model

/**
 * テスト用の架空のItemデータクラス。
 */
data class Item (
        var id: String?,
        var name: String?,
        var amount: Int,
        var quantity: Int,
        var customer: Customer?
)