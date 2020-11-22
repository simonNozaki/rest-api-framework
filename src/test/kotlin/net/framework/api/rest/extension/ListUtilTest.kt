package net.framework.api.rest.extension

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * ListUtil test
 */
class ListUtilTest {

    /**
     * ofPresentable
     * true
     */
    @Test
    fun test001_001() {
        val nums: List<Int?> = listOf(1, 3, 5, 7, 9)
        val optional = ListUtil.ofPresentable(nums)

        assertEquals(optional.isPresent, true)
    }

    /**
     * ofPresentable
     * true
     */
    @Test
    fun test001_002() {
        val nums: List<Int?> = listOf(1, 3, 5, 7, null)
        val optional = ListUtil.ofPresentable(nums)

        assertEquals(optional.isPresent, true)
    }

    /**
     * ofPresentable
     * false
     */
    @Test
    fun test001_003() {
        val nums: List<Int?> = listOf()
        val optional = ListUtil.ofPresentable(nums)

        assertEquals(optional.isPresent, false)
    }

    /**
     * ofPresentable
     * null
     */
    @Test
    fun test001_004() {
        val nums: List<Int>? = null
        val optional = ListUtil.ofPresentable(nums)

        assertEquals(optional.isPresent, false)
    }

    /**
     * ofAllPresentable
     * normal
     */
    @Test
    fun test002_001() {
        val nums: List<Int?> = listOf(1, 3, 5, 7, 9)
        val optional = ListUtil.ofAllPresentable(nums)

        assertEquals(optional.isPresent, true)
    }

    /**
     * ofAllPresentable
     * a list has null
     */
    @Test
    fun test002_002() {
        val nums: List<Int?> = listOf(1, 3, 5, 7, null)
        val optional = ListUtil.ofAllPresentable(nums)

        assertEquals(optional.isPresent, false)
    }


}