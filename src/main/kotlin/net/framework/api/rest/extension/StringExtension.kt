package net.framework.api.rest.extension

import java.lang.NumberFormatException

/** ------------------------
 * String Extensions.
------------------------ */

/**
 * Return strings list that has some incremented numbers.
 * This function increment is useful a scene like id having a string prefix and numbers as suffix.
 * @param counter increment counter
 * @return a Strings list
 * @throws NumberFormatException
 */
fun String.increment(counter: Int): MutableList<String> = increment0(counter, 1)

/**
 * Return strings list that has some incremented numbers.
 * This function increment is useful a scene like id having a string prefix and numbers as suffix.
 * @param counter increment counter
 * @param radix an incrementing radix
 * @return a Strings list
 * @throws NumberFormatException
 */
fun String.increment(counter: Int, radix: Int): MutableList<String> = increment0(counter, radix)

@Throws(exceptionClasses = [NumberFormatException::class])
private fun String.increment0(counter: Int, radix: Int): MutableList<String> {
    // return empty list if counter is less than or equal to 0
    if (counter <= 0) return mutableListOf()

    var index: Int = 0
    val zero: Char = '\u0030'

    // increment index to the border of string and integer
    // increment counter if target cannot parse to integer
    // not accept starting with 0
    for (char: Char in this) {
        if (char.toString().toIntOrNull() == null || (char.toString().toIntOrNull() != null && char == zero)) index++
        else break
    }

    val prefix: String = this.substring(0 until index)
    // can accept by 2^64(18446744073709551616)
    val number: Long = this.substring(index until this.length).toLong()

    val output: MutableList<String> = mutableListOf()

    for (i: Int in 0 until counter) {
        output.add(prefix + (number + (i * radix)))
    }

    return output
}