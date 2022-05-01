package org.hrw.mashupbackend.web

import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.core.publisher.Mono

abstract class BaseBlockchairRequest(
    webClient: WebClient,
) : BaseWebClientRequest(webClient) {
    internal inline fun <reified T : Any> getRequest(
        path: String,
        pathParams: MutableMap<String, Any>,
        queryParams: MultiValueMap<String, String>
    ): Mono<T> {
        val factory = DefaultUriBuilderFactory(path)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT

        return webclient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path(path)
                    .queryParams(queryParams)
                    .build(pathParams)
            }
            .retrieve()
            .bodyToMono()
    }
}