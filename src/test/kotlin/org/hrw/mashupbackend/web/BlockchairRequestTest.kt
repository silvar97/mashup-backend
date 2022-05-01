package org.hrw.mashupbackend.web

import com.fasterxml.jackson.core.type.TypeReference
import com.squareup.okhttp.mockwebserver.MockResponse
import org.hrw.mashupbackend.web.model.BlockchairNewsResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.util.LinkedMultiValueMap

class BlockchairRequestTest : BaseWebTest() {

    @Value("classpath:json/blockchair_news.json")
    private lateinit var newsResource: Resource

    private val blockchairRequest: BlockchairRequest = BlockchairRequest(webClient)

    @Test
    fun `WHEN Blockchair requestNews is requested THEN should return mocked Data from Mockserver`() {

        val expectedDataJson = newsResource.file.readText()
        val expectedNewsResponse =
            objectMapper.readValue(expectedDataJson, object : TypeReference<BlockchairNewsResponse>() {})

        mockBackEnd.enqueue(
            MockResponse().setBody(expectedDataJson)
                .addHeader("Content-Type", "application/json")
        )
        val queryParam = LinkedMultiValueMap<String, String>()
        val responseData = blockchairRequest.requestNews(queryParam).block()
        Assertions.assertEquals(responseData, expectedNewsResponse)

    }
}