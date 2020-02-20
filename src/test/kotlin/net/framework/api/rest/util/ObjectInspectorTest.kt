package net.framework.api.rest.util

import com.fasterxml.jackson.databind.ObjectMapper
import net.framework.api.rest.data.Order
import net.framework.api.rest.model.Errors
import net.framework.api.rest.data.TestCustomer
import net.framework.api.rest.data.TestItem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

/**
 * ObjectInspectorテストクラス。
 */
class ObjectInspectorTest {

    /**
     * 正常系
     * isNullをスルーする
     */
    @Test
    fun test001_001() {

        var testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))

        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .isNull(testItem, "null")
                .isNull(testItem?.id, "is is null")
                .isNull(testItem?.name, "name is null")
                .isNull(testItem?.customer, "customer is null")
                .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 正常系
     * hasNullValueをスルーする
     */
    @Test
    fun test001_002() {
        val testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))

        // エラーの構築
        val errors: Errors = ObjectInspector.of(testItem)
            .hasNullValue("request is null")
            .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 正常系
     * testをスルーする
     */
    @Test
    fun test001_003() {
        val testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))

        // エラーの構築
        val errors: Errors = ObjectInspector.of(testItem)
            .test({it?.name.isNullOrBlank()}, "name is null or blank")
            .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 正常系
     * violateSpecificLengthをスルーする
     */
    @Test
    fun test001_004() {
        var testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))

        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
            .violateSpecificLength(testItem?.id, 11, "digits of id is not 11")
            .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 正常系
     * violateMaxLengthをスルーする
     */
    @Test
    fun test001_005() {
        var testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))

        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
            .violateMaxLength(testItem?.name, 40, "digits of name is not within 40")
            .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 正常系
     * logメソッドの呼び出し確認
     */
    @Test
    fun test001_006() {
        var testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))

        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
            .log<TestItem>("", "")
            .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 不正系
     * 名前が空欄, testメソッド
     */
    @Test
    fun test002() {

        var testItem: TestItem? = TestItem("merchant001", "", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .test({it?.name.isNullOrBlank()}, "name is blank")
                .build()

        // アサーション
        assertEquals(errors.codes[0], "name is blank")
    }

    /**
     * 不正系
     * 名前がnull
     */
    @Test
    fun test003() {
        var testItem: TestItem? = TestItem("merchant001", null, 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .isNull(testItem?.name, "name is null")
                .build()

        // アサーション
        assertEquals(errors.codes[0], "name is null")
    }

    /**
     * 不正系
     * リクエストがnull
     */
    @Test
    fun test004() {
        var testItem: TestItem? = null
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .isNull(testItem, "item is null")
                .build()

        // アサーション
        assertEquals(errors.codes[0], "item is null")
    }

    /**
     * 不正系
     * 空欄とnullのミックス
     */
    @Test
    fun test005() {
        var testItem: TestItem? = TestItem("", null, 1000, 1, TestCustomer("", "Patrick Collison", 0, 31))
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .test({ it?.id.isNullOrBlank() }, "id is null or blank")
                .isNull(testItem?.name, "name is null")
                .test({ it?.customer?.id.isNullOrBlank() }, "customer id is null or blank")
                .test({ it?.customer?.name.isNullOrBlank() }, "customer name is null or blank")
                .build()

        println(ObjectMapper().writeValueAsString(errors))

        // アサーション
        assertAll("test005",
                { assertEquals(errors.codes[1], "id is null or blank") },
                { assertEquals(errors.codes[2], "name is null") },
                { assertEquals(errors.codes[0], "customer id is null or blank") }
        )
    }

    /**
     * 不正系
     * IDの桁数不正, 桁数不足
     */
    @Test
    fun test006() {
        var testItem: TestItem? = TestItem("merchant", null, 1000, 1, TestCustomer("", "Patrick Collison", 0, 31))
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .violateSpecificLength(testItem?.id, 10, "digits of id is not 10")
                .build()

        println(ObjectMapper().writeValueAsString(errors))

        // アサーション
        assertEquals(errors.codes[0], "digits of id is not 10")
    }

    /**
     * 不正系
     * IDの桁数不正, null
     */
    @Test
    fun test007() {
        var testItem: TestItem? = TestItem(null, null, 1000, 1, TestCustomer("", "Patrick Collison", 0, 31))
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .violateSpecificLength(testItem?.id, 10, "digits of id is null")
                .build()

        println(ObjectMapper().writeValueAsString(errors))

        // アサーション
        assertEquals(errors.codes[0], "digits of id is null")
    }

    /**
     * 不正系
     * IDの桁数不正, 空文字
     */
    @Test
    fun test008() {
        var testItem: TestItem? = TestItem("", null, 1000, 1, TestCustomer("", "Patrick Collison", 0, 31))
        // エラーの構築
        var errors: Errors = ObjectInspector.of(testItem)
                .violateSpecificLength(testItem?.id, 10, "digits of id is null")
                .build()

        println(ObjectMapper().writeValueAsString(errors))

        // アサーション
        assertEquals(errors.codes[0], "digits of id is null")
    }

    /**
     * 不正系
     * IDの桁数が規定以上である
     */
    @Test
    fun test009() {
        val testItem: TestItem? = TestItem("aaaaaaaaaaaa", null, 1000, 1, TestCustomer("", "Patrick Collison", 0, 31))
        // エラーの構築
        val errors: Errors = ObjectInspector.of(testItem)
            .violateMaxLength(testItem?.id, 10, "digits of id is over 10")
            .build()

        println(ObjectMapper().writeValueAsString(errors))

        // アサーション
        assertEquals(errors.codes[0], "digits of id is over 10")
    }

    /**
     * 不正系
     * リクエストオブジェクトがnull
     */
    @Test
    fun test010() {
        val testItem: TestItem? = null
        // エラーの構築
        val errors: Errors = ObjectInspector.of(testItem)
            .hasNullValue("request is null")
            .build()

        println(ObjectMapper().writeValueAsString(errors))

        // アサーション
        assertEquals(errors.codes[0], "request is null")
    }

    /**
     * 正常系
     * testFromIterable
     */
    @Test
    fun test011_001() {
        val order = Order(
                "20200220-00001",
                "merchant001",
                mutableListOf(
                        TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31)),
                        TestItem("merchant001", "bag", 2000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))
                )
        )

        val errors: Errors = ObjectInspector.of(order)
                .testFromIterable(order.items, { it.id.isNullOrBlank() }, "items of order has null id")
                .build()

        assertEquals(errors.codes.size, 0)
    }

    /**
     * 異常系
     * testFromIterable
     */
    @Test
    fun test011_002() {
        val order = Order(
                "20200220-00001",
                "merchant001",
                mutableListOf(
                        TestItem("", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31)),
                        TestItem("merchant001", "bag", 2000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))
                )
        )

        val errors: Errors = ObjectInspector.of(order)
                .testFromIterable(order.items, { it.id.isNullOrBlank() }, "items of order has null id")
                .build()

        assertAll("test011_002",
                { assertEquals (errors.codes.size, 1) },
                { assertEquals(errors.codes[0], "items of order has null id") }
        )
    }

    /**
     * 異常系
     * testFromIterable
     */
    @Test
    fun test011_003() {
        val order = Order(
                "20200220-00001",
                "merchant001",
                mutableListOf(
                        TestItem("", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31)),
                        TestItem("", "bag", 2000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))
                )
        )

        val errors: Errors = ObjectInspector.of(order)
                .testFromIterable(order.items, { it.id.isNullOrBlank() }, "items of order has null id")
                .build()

        println(ObjectMapper().writeValueAsString(errors))

        assertAll("test011_003",
                { assertEquals (errors.codes.size, 2) },
                { assertEquals(errors.codes[0], "items of order has null id") },
                { assertEquals(errors.codes[1], "items of order has null id") }
        )
    }

    /**
     * 異常系
     * testFromIterable
     */
    @Test
    fun test011_004() {
        val order = Order(
                "20200220-00001",
                "merchant001",
                mutableListOf(
                        TestItem("", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31)),
                        TestItem("merchant001", "bag", 2000, 1, null)
                )
        )

        val errors: Errors = ObjectInspector.of(order)
                .testFromIterable(order.items, { it.id.isNullOrBlank() }, "items of order has null id")
                .testFromIterable(order.items, { it.customer == null }, "customer information is null")
                .build()

        println(ObjectMapper().writeValueAsString(errors))

        assertAll("test011_004",
                { assertEquals (errors.codes.size, 2) },
                { assertEquals(errors.codes[1], "items of order has null id") },
                { assertEquals(errors.codes[0], "customer information is null") }
        )
    }

}