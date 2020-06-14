package net.framework.api.rest.extension.stringextension

import net.framework.api.rest.extension.increment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.Assertions.*

/**
 * StringExtension Test class
 */
class IncrementTest {

    /**
     * normal case
     * parse suffix as a normal number
     */
    @Test
    fun test001_001() {
        val result: MutableList<String> = "user-202006130001".increment(counter = 2)

        result.forEach{ println(it) }

        assertAll("test001_001",
            { assertEquals(result.size, 2) },
            { assertEquals(result[0], "user-202006130001") },
            { assertEquals(result[1], "user-202006130002") }
        )
    }

    /**
     * normal case
     * only number start with not 0
     */
    @Test
    fun test001_002() {
        val result: MutableList<String> = "202006130001".increment(counter = 2)

        result.forEach{ println(it) }

        assertAll("test001_002",
            { assertEquals(result.size, 2) },
            { assertEquals(result[0], "202006130001") },
            { assertEquals(result[1], "202006130002") }
        )
    }

    /**
     * normal case
     * start with 0
     */
    @Test
    fun test001_003() {
        val result: MutableList<String> = "00000001".increment(counter = 3)

        result.forEach{ println(it) }

        assertAll("test001_003",
            { assertEquals(result.size, 3) },
            { assertEquals(result[0], "00000001") },
            { assertEquals(result[2], "00000003") }
        )
    }

    /**
     * error case
     * counter is less than 0
     */
    @Test
    fun test002_001() {

        val result: MutableList<String> = "user-202006130001".increment(-1)

        assertAll("test002_001",
            { assertEquals(result.size, 0) }
        )
    }
}