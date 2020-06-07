package net.framework.api.rest.client

import net.framework.api.rest.extension.ObjectUtil
import org.junit.jupiter.api.Assertions.*
import net.framework.api.rest.model.Customer
import net.framework.api.rest.model.Item
import net.framework.api.rest.model.Order
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
     * 正常系
     */
    @Test
    fun test001_001() {
        // setup mock server
        mockServerClient
            .`when`(request()
                    .withMethod("POST")
                    .withPath("/order")
                    .withBody(MappingSingleton.getMapper().toJson(createNormalRequest()))
            )
            .respond(response()
                    .withStatusCode(200)
                    .withHeader(Header("Content-Type", "application/json; charset=utf-8"))
                    .withBody(MappingSingleton.getMapper().toJson(createNormalResponse()))
            )
        // call mock API
        val result: Response = SimpleApiClient
            .setTargetUri("http://localhost:8080/order")
            .setHeader("Content-Type", "application/json")
            .post(createNormalRequest())
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
     * 正常なOrderインスタンスを一件返却します
     */
    private fun createNormalRequest(): Order =
        Order(
            "20200220-00001",
            "merchant001",
            mutableListOf(
                Item("merchant001", "hat", 1000, 1, Customer("customer001", "Patrick Collison", 0, 31)),
                Item("merchant001", "bag", 2000, 1, Customer("customer001", "Patrick Collison", 0, 31))
            )
        )

    /**
     * 正常なResponseインスタンスを一つ返却します
     * @return
     */
    private fun createNormalResponse(): Response =
        Response(
            "20200220-00001",
            "merchant001",
            null
        )
}