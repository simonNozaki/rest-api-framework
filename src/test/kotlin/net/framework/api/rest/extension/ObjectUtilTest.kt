package net.framework.api.rest.extension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import java.util.stream.Stream

/**
 * Objectユーティリティテストクラス。
 */
class ObjectUtilTest {

    /**
     * isUrl()
     * URL形式である
     */
    @Test
    fun test001_001() {
        assertEquals(ObjectUtil.isUri("https://github.com"), true)
    }

    /**
     * isUrl()
     * http URIではない
     */
    @Test
    fun test001_002() {
        assertEquals(ObjectUtil.isUri("sftp://github.com"), false)
    }

    /**
     * isUrl()
     * URIではない、単純な文字列
     */
    @Test
    fun test001_003() {
        assertEquals(ObjectUtil.isUri("Kotlin"), false)
    }

    /**
     * isUrl()
     * null
     */
    @Test
    fun test001_004() {
        assertEquals(ObjectUtil.isUri(null), false)
    }

    /**
     * isUrl()
     * 空文字
     */
    @Test
    fun test001_005() {
        assertEquals(ObjectUtil.isUri(""), false)
    }

    /**
     * convertToList()
     * リストが生成できる
     */
    @Test
    fun test002_001() {
       val list = ObjectUtil.convertToList("java", "kotlin", "go")
        assertAll("test002_001",
            { assertEquals(list.size, 3) },
            { assertEquals(list[0], "java") },
            { assertEquals(list[1], "kotlin") }
        )
    }

    /**
     * isNullOrEmpty()
     * null
     */
    @Test
    fun test003_001() {
        assertEquals(ObjectUtil.isNullOrEmpty(null), true)
    }

    /**
     * isNullOrEmpty()
     * 空文字
     */
    @Test
    fun test003_002() {
        assertEquals(ObjectUtil.isNullOrEmpty(""), true)
    }

    /**
     * isNullOrEmpty()
     * 空のリストあり、false
     */
    @Test
    fun test003_003() {
        assertEquals(ObjectUtil.isNullOrEmpty(mutableListOf<String>()), false)
    }

    /**
     * getStream()
     * Streamを得る
     */
    @Test
    fun test004_001() {
        val stream = ObjectUtil.getStream(mutableListOf<String>())
        assertEquals(stream is Stream<String>, stream is Stream<String>)
    }

    /**
     * getStream()
     * 空のStreamを得る
     */
    @Test
    fun test004_002() {
        val stream = ObjectUtil.getStream<String>(null)
        assertEquals(stream is Stream<String>, stream is Stream<String>)
    }
}