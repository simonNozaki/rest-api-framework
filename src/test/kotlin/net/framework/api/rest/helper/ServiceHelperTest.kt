package net.framework.api.rest.helper

import net.framework.api.rest.data.TestCustomer
import net.framework.api.rest.data.TestItem
import net.framework.api.rest.model.Errors
import net.framework.api.rest.model.ServiceOut
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

/**
 * ServiceHelperテストクラス。
 */
class ServiceHelperTest : ServiceHelper() {

    /**
     * 正常系
     * ビルダーを呼び出せる
     */
    @Test
    fun test001_001() {
        val testItem: TestItem? = TestItem("merchant001", "hat", 1000, 1, TestCustomer("customer001", "Patrick Collison", 0, 31))
        val out: ServiceOut<TestItem?> = doPipeServiceOut<TestItem>()
                .setResult(testItem)
                .build()

        assertAll("test001_001",
                { assertEquals(out::class.java, ServiceOut::class.java) },
                { assertEquals(out.value?.customer?.name, "Patrick Collison") }
        )
    }

    /**
     * 正常系
     * エラーを設定して呼び出せる
     */
    fun test001_002() {
        val errors = Errors().apply { codes = mutableListOf("system error") }
        val out: ServiceOut<Any> = doPipeServiceOut<Any>()
                .setErrors(errors.codes)
                .build()

        assertAll("test001_002",
                { assertEquals(out::class.java, ServiceOut::class.java) },
                { assertEquals(out.value, null) },
                { assertEquals(out.errors?.codes?.get(0), "system error") }
        )
    }
}