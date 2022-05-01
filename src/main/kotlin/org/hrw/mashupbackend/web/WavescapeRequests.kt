package org.hrw.mashupbackend.web

import org.hrw.mashupbackend.web.model.WavesAssetsResponse
import org.hrw.mashupbackend.web.model.WavesTotalResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class WavescapeRequests(
    @Qualifier("wavescapeClient") webClient: WebClient,
) : BaseWavescapeRequest(webClient) {

    fun requestAssetsData(): Mono<WavesAssetsResponse> {
        return getRequest(assetsPath, emptyPathParamMap(), emptyQueryParamMap())
    }

    fun requestTotals(): Mono<WavesTotalResponse> {
        return getRequest(totalsPath, emptyPathParamMap(), emptyQueryParamMap())
    }

    companion object {
        const val assetWavePath = "/asset/WAVES.json"
        const val assetsPath = "/assets.json"
        const val totalsPath = "/totals.json"
    }
}