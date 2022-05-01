package org.hrw.mashupbackend.web

import com.fasterxml.jackson.core.type.TypeReference
import com.squareup.okhttp.mockwebserver.MockResponse
import org.hrw.mashupbackend.web.model.WavesAssetsResponse
import org.hrw.mashupbackend.web.model.WavesTotalResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource

class WavescapeRequestTest : BaseWebTest() {

    @Value("classpath:json/wavescape_assets.json")
    private lateinit var assetsResource: Resource

    @Value("classpath:json/wavescape_totals.json")
    private lateinit var totalsResource: Resource

    val wavescapeRequests: WavescapeRequests = WavescapeRequests(webClient)

    @Test
    fun `WHEN getAssets is requested THEN should return data from MockedServer`() {

        val expectedDataJson = assetsResource.file.readText()
        val expectedAssetResponse =
            objectMapper.readValue(expectedDataJson, object : TypeReference<WavesAssetsResponse>() {})

        mockBackEnd.enqueue(
            MockResponse().setBody(expectedDataJson)
                .addHeader("Content-Type", "application/json")
        )
        val responseData = wavescapeRequests.requestAssetsData().block()
        Assertions.assertEquals(responseData, expectedAssetResponse)
    }
    @Test
    fun `WHEN getTotals is requested THEN should return data from MockedServer`() {

        val expectedDataJson = totalsResource.file.readText()
        val expectedTotalsResponse =
            objectMapper.readValue(expectedDataJson, object : TypeReference<WavesTotalResponse>() {})

        mockBackEnd.enqueue(
            MockResponse().setBody(expectedDataJson)
                .addHeader("Content-Type", "application/json")
        )
        val responseData = wavescapeRequests.requestTotals().block()
        Assertions.assertEquals(responseData, expectedTotalsResponse)
    }
}