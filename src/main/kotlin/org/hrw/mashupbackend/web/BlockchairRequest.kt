package org.hrw.mashupbackend.web

import org.hrw.mashupbackend.web.model.BlockchairNewsResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class BlockchairRequest(
    @Qualifier("blockchairClient") webclient: WebClient
) : BaseBlockchairRequest(webclient) {
    fun requestNews(queryParam: MultiValueMap<String, String>): Mono<BlockchairNewsResponse> {
        return getRequest(newsPath, emptyPathParamMap(), queryParam)
    }

    companion object {
        const val newsPath = "/news"
    }
}