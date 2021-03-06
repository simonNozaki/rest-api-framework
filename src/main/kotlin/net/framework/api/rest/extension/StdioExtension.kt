package net.framework.api.rest.extension

import java.util.*

/** ------------------------
 * Standard IO Extensions.
------------------------ */

/**
 * Print stack trace info.
 * code:
 * ```
 * "rest-api-framework".printStack("message")
 * ```
 * and output:
 * ```
 * Sat May 33 00:00:00 JST 2020 [ main ] rest-api-framework StdioExtension#printStack message
 * ```
 *
 */
fun String.printStack(message: String)
 = "${Calendar.getInstance().time} [ ${Thread.currentThread().name} ] $this ${Throwable().stackTrace[1].className}#${Throwable().stackTrace[1].methodName} $message"
