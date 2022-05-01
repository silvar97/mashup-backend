package org.hrw.mashupbackend.service

import org.hrw.mashup.backend.types.News
import org.hrw.mashup.backend.types.Sentiment
import org.hrw.mashupbackend.service.model.SentimentAnalysisEnum
import org.hrw.mashupbackend.web.BlockchairRequest
import org.hrw.mashupbackend.web.model.BlockchairNewsResponse
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Service
class BlockchairService(
    val blockchairRequest: BlockchairRequest,
    val meaningCloudService: MeaningCloudService
) {

    enum class Attributes {
        Waves,
        BTC,
        ETH
    }

    private val data: MutableMap<Attributes, BlockchairNewsResponse> = ConcurrentHashMap()

    init {
        loadNewsData()
    }

    @Scheduled(cron = "\${scheduling.time.blockchair.news}")
    private fun loadNewsData() {
        val wavesQuery = createQueryForNews(Attributes.Waves)
        blockchairRequest.requestNews(wavesQuery)
            .doOnError { WavescapeService.logger.error("something went wrong while loading waves news data from blockchair") }
            .subscribe {
                data[Attributes.Waves] = it
            }
        val btcQuery = createQueryForNews(Attributes.BTC)
        blockchairRequest.requestNews(btcQuery)
            .doOnError { WavescapeService.logger.error("something went wrong while loading BTC news data from blockchair") }
            .subscribe {
                data[Attributes.BTC] = it
            }
        val ethQuery = createQueryForNews(Attributes.ETH)
        blockchairRequest.requestNews(ethQuery)
            .doOnError { WavescapeService.logger.error("something went wrong while loading ETH news data from blockchair") }
            .subscribe {
                data[Attributes.ETH] = it
            }
    }

    fun getNewsForWaves(): Mono<List<News>> {
        return Mono.justOrEmpty(data[Attributes.Waves])

            .mapNotNull { news ->
                news.data?.mapNotNull {
//                    val sentiment = it?.description?.let { it1 -> getSentimentForNews(it1) }
                    News(
                        name = "Waves",
                        title = it?.title,
                        description = it?.description,
                        time = it?.time,
//                            sentiment = sentiment?.block()
                    )
                }
            }

    }

    fun getNewsForEth(): Mono<List<News>> {
        return Mono.justOrEmpty(data[Attributes.ETH])

            .mapNotNull { news ->
                news.data?.mapNotNull {
//                    val sentiment = it?.description?.let { it1 -> getSentimentForNews(it1) }
                    News(
                        name = "Ethereum",
                        title = it?.title,
                        description = it?.description,
                        time = it?.time,
//                            sentiment = sentiment?.block()
                    )
                }
            }

    }

    fun getNewsForBtc(): Mono<List<News>> {
        return Mono.justOrEmpty(data[Attributes.BTC])

            .mapNotNull { news ->
                news.data?.mapNotNull {
//                    val sentiment = it?.description?.let { it1 -> getSentimentForNews(it1) }
                    News(
                        name = "Bitcoin",
                        title = it?.title,
                        description = it?.description,
                        time = it?.time,
//                            sentiment = sentiment?.block()
                    )
                }
            }

    }

    private fun getSentimentForNews(description: String): Mono<Sentiment> {
        return meaningCloudService.getSentimentAnalysisFromText(description).map {
            when (it) {
                SentimentAnalysisEnum.GOOD -> Sentiment.GOOD
                SentimentAnalysisEnum.BAD -> Sentiment.BAD
                SentimentAnalysisEnum.NEUTRAL -> Sentiment.NEUTRAL
            }
        }
    }

    companion object {
        fun createQueryForNews(searchValue: Attributes): LinkedMultiValueMap<String, String> {
            val query = LinkedMultiValueMap<String, String>()
            val q = when (searchValue) {
                Attributes.Waves -> "language(en),title(~waves),or,description(~waves)"
                Attributes.BTC -> "language(en),title(~bitcoin),or,title(~btc),or,description(~btc),or,description(~bitcoin)"
                Attributes.ETH -> "language(en),title(~ethereum),or,title(~eth),or,description(~ethereum),or,description(~eth)"
            }
            query.add("q", q)
            return query
        }


    }
}