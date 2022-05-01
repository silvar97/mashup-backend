package org.hrw.mashupbackend.service

import com.fasterxml.jackson.core.type.TypeReference
import com.nhaarman.mockito_kotlin.whenever
import org.hrw.mashup.backend.types.Currency
import org.hrw.mashupbackend.web.WavescapeRequests
import org.hrw.mashupbackend.web.model.WavesAssetsResponse
import org.hrw.mashupbackend.web.model.WavesTotalResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import reactor.core.publisher.Mono

@SpringBootTest
class WavescapeServiceTest : AbstractServiceTest() {

    @Value("classpath:json/wavescape_assets.json")
    private lateinit var assetsResource: Resource

    @Value("classpath:json/wavescape_totals.json")
    private lateinit var totalsResource: Resource

    @Mock
    private lateinit var wavescapeRequests: WavescapeRequests

    private lateinit var wavescapeService: WavescapeService

    @BeforeEach
    fun setup() {
        wavescapeService = WavescapeService(wavescapeRequests)
        val expectedDataJson = assetsResource.file.readText()
        val expectedAssetResponse =
            objectMapper.readValue(expectedDataJson, object : TypeReference<WavesAssetsResponse>() {})
        val expectedDataTotalsJson = totalsResource.file.readText()

        val expectedTotalResponse =
            objectMapper.readValue(expectedDataTotalsJson, object : TypeReference<WavesTotalResponse>() {})
        whenever(wavescapeRequests.requestAssetsData()).thenReturn(Mono.just(expectedAssetResponse))

        whenever(wavescapeRequests.requestTotals()).thenReturn(Mono.just(expectedTotalResponse))
        wavescapeService.loadDataFromWavescape()
    }

    @Test
    fun `WHEN getAssetsFromWavescape is called should return list of Assets`() {
        val assetsList = wavescapeService.getAssetsFromWavescape(Currency.EURO).block()
        Assertions.assertEquals(assetsList?.size, 3)
    }

    @Test
    fun `WHEN getTotalsFromWavescape is called should return Total`() {
        val totals = wavescapeService.getTotalsFromWavesecape(Currency.EURO).block()
        println(totals)
        Assertions.assertEquals(
            totals.toString(),
            "Total(totalCap=3.0387977904329E9, gateways=5.0344613717396E8, assets=1.2009846743339E9, vol24H=1.5466462462268E7, trades24H=15597)"
        )
    }

}