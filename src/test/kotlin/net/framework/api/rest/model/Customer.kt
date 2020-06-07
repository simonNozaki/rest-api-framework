package net.framework.api.rest.model

/**
 * テスト用の架空Customerデータクラス。
 */
data class Customer (
        var id: String,
        var name: String,
        var gender: Int,
        var age: Int
)