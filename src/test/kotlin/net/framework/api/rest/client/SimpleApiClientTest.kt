package net.framework.api.rest.client

import net.framework.api.rest.extension.ObjectUtil
import org.junit.jupiter.api.Assertions.*
import net.framework.api.rest.helper.MappingSingleton
import net.framework.api.rest.model.Response
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockserver.client.MockServerClient
import org.mockserver.junit.jupiter.MockServerExtension
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.integration.ClientAndServer.startClientAndServer
import net.framework.api.rest.model.createNormalRequest
import net.framework.api.rest.model.createNormalResponse

/**
 * シンプルAPIクライアントテストクラス。
 */
@ExtendWith(MockServerExtension::class)
class SimpleApiClientTest {

    private lateinit var mockServerClient: MockServerClient

    @BeforeEach
    fun beforeEachLifeCycle() {
        mockServerClient = startClientAndServer(8080)
    }

    @AfterEach
    fun afterEachLifeCycle() {
        mockServerClient.stop()
    }

    /**
     * normal case, post
     */
    @Test
    fun test001_001() {
        // setup mock server
        mockServerClient
            .`when`(request()
                    .withMethod("POST")
                    .withPath("/order")
                    .withBody(MappingSingleton.getMapper().toJson(createNormalRequest(orderId = "20200220-00001", merchantId = "merchant001")))
            )
            .respond(response()
                    .withStatusCode(200)
                    .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                    .withBody(MappingSingleton.getMapper().toJson(createNormalResponse(orderId = "20200220-00001", merchantId = "merchant001")))
            )
        // call mock API
        val result: Response = SimpleApiClient
            .setTargetUri("http://localhost:8080/order")
            .setHeader("Content-Type", "application/json")
            .post(createNormalRequest(orderId = "20200220-00001", merchantId = "merchant001"))
            .invoke(Response::class.java)
        // assertion
        assertAll(
            "test001_001",
            { assertEquals(ObjectUtil.isNullOrEmpty(result), false) },
            { assertEquals(result.orderId, "20200220-00001") },
            { assertEquals(result.merchantId, "merchant001") },
            { assertEquals(result.errors, null) }
        )
    }

    /**
     * normal case, get
     */
    @Test
    fun test001_002() {
        // setup mock server
        mockServerClient
            .`when`(request()
                .withMethod("GET")
                .withPath("/order")
            )
            .respond(response()
                .withStatusCode(200)
                .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(MappingSingleton.getMapper().toJson(createNormalResponse(orderId = "20200220-00001", merchantId = "merchant001")))
            )
        // call mock API
        val result: Response = SimpleApiClient
            .setTargetUri("http://localhost:8080/order")
            .setHeader("Content-Type", "application/json")
            .get()
            .invoke(Response::class.java)
        // assertion
        assertAll(
            "test001_002",
            { assertEquals(ObjectUtil.isNullOrEmpty(result), false) },
            { assertEquals(result.orderId, "20200220-00001") },
            { assertEquals(result.merchantId, "merchant001") },
            { assertEquals(result.errors, null) }
        )
    }

    /**
     * normal case, put
     */
    @Test
    fun test001_003() {
        // setup mock server
        mockServerClient
            .`when`(request()
                .withMethod("PUT")
                .withPath("/order")
                .withBody(MappingSingleton.getMapper().toJson(createNormalRequest(orderId = "20200220-00001", merchantId = "merchant001")))
            )
            .respond(response()
                .withStatusCode(200)
                .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(MappingSingleton.getMapper().toJson(createNormalResponse(orderId = "20200220-00001", merchantId = "merchant001")))
            )
        // call mock API
        val result: Response = SimpleApiClient
            .setTargetUri("http://localhost:8080/order")
            .setHeader("Content-Type", "application/json")
            .put(createNormalRequest(orderId = "20200220-00001", merchantId = "merchant001"))
            .invoke(Response::class.java)
        // assertion
        assertAll(
            "test001_003",
            { assertEquals(ObjectUtil.isNullOrEmpty(result), false) },
            { assertEquals(result.orderId, "20200220-00001") },
            { assertEquals(result.merchantId, "merchant001") },
            { assertEquals(result.errors, null) }
        )
    }

    /**
     * normal case, delete
     */
    @Test
    fun test001_004() {
        // setup mock server
        mockServerClient
            .`when`(request()
                .withMethod("DELETE")
                .withPath("/order")
                .withBody(MappingSingleton.getMapper().toJson(createNormalRequest(orderId = "20200220-00001", merchantId = "merchant001")))
            )
            .respond(response()
                .withStatusCode(200)
                .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                .withBody(MappingSingleton.getMapper().toJson(createNormalResponse(orderId = "20200220-00001", merchantId = "merchant001")))
            )
        // call mock API
        val result: Response = SimpleApiClient
            .setTargetUri("http://localhost:8080/order")
            .setHeader("Content-Type", "application/json")
            .delete(createNormalRequest(orderId = "20200220-00001", merchantId = "merchant001"))
            .invoke(Response::class.java)
        // assertion
        assertAll(
            "test001_004",
            { assertEquals(ObjectUtil.isNullOrEmpty(result), false) },
            { assertEquals(result.orderId, "20200220-00001") },
            { assertEquals(result.merchantId, "merchant001") },
            { assertEquals(result.errors, null) }
        )
    }

}