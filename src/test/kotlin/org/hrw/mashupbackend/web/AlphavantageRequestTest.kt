package org.hrw.mashupbackend.web

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.squareup.okhttp.mockwebserver.MockResponse
import org.hrw.mashupbackend.web.model.AlphavantageHistoricalDataResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.util.LinkedMultiValueMap

class AlphavantageRequestTest : BaseWebTest() {

    @Value("classpath:json/alphavantage_historical_data.json")
    private lateinit var alphavantageHistoryDataResource: Resource

    private val alphavantageRequest = AlphavantageRequest(webClient)
    @BeforeEach
    fun init(){
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false)
    }
    @Test
    fun `WHEN Alphavantage requestData is called THEN should return mocked data from MockServer`() {
        val expectedDataJson = alphavantageHistoryDataResource.file.readText()
        val expectedAlphavanatageResponse =
            objectMapper.readValue(expectedDataJson, object : TypeReference<AlphavantageHistoricalDataResponse>() {})

        mockBackEnd.enqueue(
            MockResponse().setBody(expectedDataJson)
                .addHeader("Content-Type", "application/json")
        )
        val queryParam = LinkedMultiValueMap<String, String>()
        val responseData = alphavantageRequest.requestData(queryParam).block()
        Assertions.assertEquals(responseData, expectedAlphavanatageResponse)
    }


}