package net.framework.api.rest.util

import com.fasterxml.jackson.databind.ObjectMapper
import net.framework.api.rest.Errors
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
     */
    @Test
    fun test001() {

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
}