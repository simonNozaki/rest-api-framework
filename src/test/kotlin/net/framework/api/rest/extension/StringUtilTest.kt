package net.framework.api.rest.extension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * 文字列ユーティリティテストクラス。
 */
class StringUtilTest {

    /**
     * isNullOrBlank()
     * 空文字、trueになる
     */
    @Test
    fun test001_001() {
       assertEquals(StringUtil.isNullOrBlank(""), true)
    }

    /**
     * isNullOrBlank()
     * null、trueになる
     */
    @Test
    fun test001_002() {
        assertEquals(StringUtil.isNullOrBlank(null), true)
    }

    /**
     * isNullOrBlank()
     * falseになる
     */
    @Test
    fun test001_003() {
        assertEquals(StringUtil.isNullOrBlank("test method"), false)
    }

    /**
     * convertNullToEmpty()
     * nullが空文字になる
     */
    @Test
    fun test002_001() {
        val test = StringUtil.convertNullToEmpty(null)
        assertEquals(test, "")
    }

    /**
     * convertNullToEmpty()
     * 空文字のまま、変換なし
     */
    @Test
    fun test002_002() {
        val test = StringUtil.convertNullToEmpty("")
        assertEquals(test, "")
    }

    /**
     * ofPresentable
     */
    @Test
    fun test003_001() {
        val optional = StringUtil.ofPresentable(null)
        assertEquals(optional.isPresent, false);
    }

    /**
     * ofPresentable
     * blank
     */
    @Test
    fun test003_002() {
        val optional = StringUtil.ofPresentable("")
        assertEquals(optional.isPresent, false);
    }

    /**
     * ofPresentable
     * full space
     */
    @Test
    fun test003_003() {
        val optional = StringUtil.ofPresentable("　")
        assertEquals(optional.isPresent, true)
    }

    /**
     * ofPresentable
     * not blank
     */
    @Test
    fun test003_004() {
        val optional = StringUtil.ofPresentable("ofpresentable")
        assertEquals(optional.isPresent, true)
    }


}