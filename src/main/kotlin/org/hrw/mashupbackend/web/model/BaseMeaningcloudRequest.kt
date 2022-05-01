package org.hrw.mashupbackend.web.model

import org.hrw.mashupbackend.config.properties.MeaningcloudProperties
import org.hrw.mashupbackend.web.BaseWebClientRequest
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.core.publisher.Mono

abstract class BaseMeaningcloudRequest(
    webClient: WebClient,
    private val meaningcloudProperties: MeaningcloudProperties
) : BaseWebClientRequest(webClient) {
    internal inline fun <reified T : Any> postRequest(
        path: String,
        pathParams: MutableMap<String, Any>,
        queryParams: MultiValueMap<String, String>,
        requestBody: Any
    ): Mono<T> {
        val factory = DefaultUriBuilderFactory(path)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT

        return webclient.post()
            .uri { uriBuilder ->
                uriBuilder
                    .path(path)
                    .queryParams(queryParams)
                    .build(pathParams)
            }
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono()
    }
}