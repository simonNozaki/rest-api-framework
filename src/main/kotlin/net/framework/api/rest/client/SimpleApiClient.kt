package net.framework.api.rest.client

import net.framework.api.rest.constant.AppConst
import net.framework.api.rest.exception.SimpleApiBadRequestException
import net.framework.api.rest.helper.MappingSingleton
import net.framework.api.rest.extension.ObjectUtil
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

/**
 * Simple API Client class, wrapping HttpClient included in java.net package.
 * @code SimpleApiClient.setTargetUri().setHeader("", "").get().invoke(Target.class)
 */
class SimpleApiClient {

    companion object ClientSingleton {

        private lateinit var httpHeaderKey: String
        private lateinit var httpHeaderValue: String
        private lateinit var targetUri: String
        private lateinit var httpRequest: HttpRequest

        private const val HTTP_METHOD_POST: String = "POST"
        private const val HTTP_METHOD_GET: String = "GET"
        private const val HTTP_METHOD_PUT: String = "PUT"
        private const val HTTP_METHOD_DELETE: String = "DELETE"

        /**
         * Set a target uri.
         * an Intermediate operation.
         * @param uri a target URI string
         * @return ClientSingleton object
         */
        fun setTargetUri(uri: String): ClientSingleton {
            this.targetUri = uri
            return ClientSingleton
        }

        /**
         * Set a http header and value.
         * an Intermediate operation.
         * @param headerKey
         * @param headerValue
         * @return ClientSingleton object
         */
        fun setHeader(headerKey: String, headerValue: String): ClientSingleton {
            this.httpHeaderKey = headerKey
            this.httpHeaderValue = headerValue
            return ClientSingleton
        }

        /**
         * Ready to call an API with GET method.
         * an Intermediate operation.
         * @return ClientSingleton object
         */
        fun get(): ClientSingleton {
            this.httpRequest = buildRequest(HTTP_METHOD_GET, null)
            return ClientSingleton
        }

        /**
         * Ready to call an API with POST method.
         * an Intermediate operation.
         * @param value a Request parameter for API
         * @return ClientSingleton object
         */
        fun <T> post(value: T?): ClientSingleton {
            this.httpRequest = buildRequest(HTTP_METHOD_POST, value)
            return ClientSingleton
        }

        /**
         * Ready to call an API with DELETE method.
         * an Intermediate operation.
         * @param value a Request parameter for API
         * @return ClientSingleton object
         */
        fun <T> delete(value: T?): ClientSingleton {
            this.httpRequest = buildRequest(HTTP_METHOD_DELETE, value)
            return ClientSingleton
        }

        /**
         * Ready to call an API with PUT method.
         * an Intermediate operation.
         * @param value a Request parameter for API
         * @return ClientSingleton object
         */
        fun <T> put(value: T?): ClientSingleton {
            this.httpRequest = buildRequest(HTTP_METHOD_PUT, value)
            return ClientSingleton
        }

        /**
         * Send a request to API and deserialize a response with a type of method argument.
         * @param type
         * @return ClientSingleton object
         * @throws IOException
         * @throws InterruptedException
         */
        @Throws(IOException::class, InterruptedException::class)
        operator fun <R> invoke(type: Class<R>): R {
            val httpResponse: HttpResponse<String> = HttpClient.newBuilder().build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
            return MappingSingleton.getMapper().fromJson(httpResponse.body(), type)
        }

        /**
         * Build http request.
         * @param method
         * @param value
         * @return a HttpRequest instance
         */
        private fun <T> buildRequest(method: String, value: T?): HttpRequest {
            if (method != HTTP_METHOD_GET && ObjectUtil.isNullOrEmpty(value)) throw SimpleApiBadRequestException(AppConst.WEB_API_REQUEST_ERROR)

            val builder: HttpRequest.Builder = HttpRequest.newBuilder()
                .uri(URI.create(targetUri))
                .header(this.httpHeaderKey, this.httpHeaderValue)

            return when (method) {
                HTTP_METHOD_POST -> builder
                    .POST(HttpRequest.BodyPublishers.ofString(MappingSingleton.getMapper().toJson(value)))
                    .build()
                HTTP_METHOD_GET -> builder.GET().build()
                HTTP_METHOD_PUT -> builder
                    .PUT(HttpRequest.BodyPublishers.ofString(MappingSingleton.getMapper().toJson(value)))
                    .build()
                HTTP_METHOD_DELETE -> builder.DELETE().build()
                else -> builder.build()
            }
        }

    }
}