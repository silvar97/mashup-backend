package org.hrw.mashupbackend.api

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import org.hrw.mashup.backend.types.Currency
import org.hrw.mashup.backend.types.HistoricalData
import org.hrw.mashupbackend.service.AlphavantageService
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import java.time.Duration


@DgsComponent
class AlphavantageDataFetcher(val alphavantageService: AlphavantageService) {

    @DgsQuery
    fun getHistoricalDataForWaves(@InputArgument currency: Currency): Publisher<HistoricalData> {
        return alphavantageService.getHistoricalDataForWaves(currency)
    }

    @DgsQuery
    fun getHistoricalDataForBTC(@InputArgument currency: Currency): Publisher<HistoricalData> {
        return alphavantageService.getHistoricalDataForBTC(currency)
    }

    @DgsQuery
    fun getHistoricalDataForETH(@InputArgument currency: Currency): Publisher<HistoricalData> {
        return alphavantageService.getHistoricalDataForETH(currency)
    }


    @DgsSubscription
    fun getHistoricalDataForWavesSubscription(@InputArgument currency: Currency): Publisher<HistoricalData> {
        return Flux.interval(Duration.ofMillis(0),Duration.ofSeconds(REFRESH_TIME_IN_SECONDS))
            .concatMap {
                alphavantageService.getHistoricalDataForWaves(currency)
            }

    }

    companion object {
        const val REFRESH_TIME_IN_SECONDS: Long = 10
    }
}

