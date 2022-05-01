//package org.hrw.mashupbackend.api
//
//import com.fasterxml.jackson.core.type.TypeReference
//import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
//import com.nhaarman.mockito_kotlin.any
//import com.nhaarman.mockito_kotlin.whenever
//import org.hrw.mashup.backend.types.Asset
//import org.hrw.mashupbackend.service.WavescapeService
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.test.context.ContextConfiguration
//import reactor.core.publisher.Mono
//
//@ContextConfiguration(
//    classes = [WavescapeDataFetcher::class]
//)
//class WavescapeDataFetcherTest : AbstractDataFetcherTest() {
//
//    @MockBean
//    lateinit var wavescapeService: WavescapeService
//
//    @Test
//    fun `WHEN Assets are requested should return it`() {
//        val asset = Asset(
//            name = "Waves",
//            price = "123",
//            volume = "123",
//            change = "1",
//            trades = "123",
//            totalSupply = "123",
//            id = "12333",
//            shortCode = "123"
//        )
//        whenever(wavescapeService.getAssetsFromWavescape(any())).thenReturn(
//            Mono.just(
//                listOf(
//                    asset
//                )
//            )
//        )
//        val executionResult: List<Map<String, String>> = dgsQueryExecutor.executeAndExtractJsonPath(
//            "query{getAssets(currency:EURO){id\n" +
//                    "name\n" +
//                    "shortCode\n" +
//                    "totalSupply\n" +
//                    "trades\n" +
//                    "price\n" +
//                    "change\n" +
//                    "volume}}",
//            "$.data.getAssets[*]"
//        )
//        val mappedResult: List<Asset> =
//            objectMapper.readValue(executionResult.toString(), object : TypeReference<List<Asset>>() {})
//        Assertions.assertEquals(mappedResult, listOf(asset))
//
//    }
//
//}