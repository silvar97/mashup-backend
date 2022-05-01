package org.hrw.mashupbackend.web

import org.hrw.mashupbackend.web.model.AlphavantageHistoricalDataResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class AlphavantageRequest(
    @Qualifier("alphavantageClient") webClient: WebClient,
) : BaseAlphavantageRequest(webClient) {

    fun requestData(queryParam: MultiValueMap<String, String>): Mono<AlphavantageHistoricalDataResponse> {
        return getRequest("", emptyPathParamMap(), queryParam)
    }


}