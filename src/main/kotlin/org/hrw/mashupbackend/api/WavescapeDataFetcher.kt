package org.hrw.mashupbackend.api

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import org.hrw.mashup.backend.types.Asset
import org.hrw.mashup.backend.types.Currency
import org.hrw.mashup.backend.types.Total
import org.hrw.mashupbackend.service.WavescapeService
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.kotlin.core.publisher.toFlux
import java.time.Duration
import java.util.concurrent.TimeUnit

@DgsComponent
class WavescapeDataFetcher(val wavescapeService: WavescapeService) {

    @DgsQuery
    fun getAssets(@InputArgument currency: Currency): Publisher<List<Asset>> {
        return wavescapeService.getAssetsFromWavescape(currency)
    }

    @DgsQuery
    fun getTotals(@InputArgument currency: Currency): Publisher<Total> {
        return wavescapeService.getTotalsFromWavesecape(currency)
    }

    @DgsSubscription
    fun getAssetsSubscription(@InputArgument currency: Currency): Publisher<List<Asset>> {
        return Flux
            .interval(Duration.ofMillis(0),Duration.ofSeconds(REFRESH_TIME_IN_SECONDS))
            .flatMap { wavescapeService.getAssetsFromWavescape(currency) }
    }

    @DgsSubscription
    fun getTotalsSubscription(@InputArgument currency: Currency): Publisher<Total> {
        return Flux
            .interval(Duration.ofMillis(0),Duration.ofSeconds(REFRESH_TIME_IN_SECONDS))
            .flatMap { wavescapeService.getTotalsFromWavesecape(currency) }
    }

    companion object {
        const val REFRESH_TIME_IN_SECONDS: Long = 1
    }
}