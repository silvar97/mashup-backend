package org.hrw.mashupbackend.web

import org.hrw.mashupbackend.config.properties.MeaningcloudProperties
import org.hrw.mashupbackend.web.model.BaseMeaningcloudRequest
import org.hrw.mashupbackend.web.model.MeaningCloudSentimentResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class MeaningCloudRequest(
    @Qualifier("meaningcloudClient") webClient: WebClient,
    private val meaningcloudProperties: MeaningcloudProperties
) : BaseMeaningcloudRequest(webClient, meaningcloudProperties) {

    fun requestSentimentAnalysisFor(txt: String): Mono<MeaningCloudSentimentResponse> {
        val bodyValues = createBodyRequest(txt)
        return postRequest("", emptyPathParamMap(), emptyQueryParamMap(), bodyValues)
    }

    private fun createBodyRequest(txt: String): MultiValueMap<String, String> {
        val body = LinkedMultiValueMap<String, String>()
        body.add("key", meaningcloudProperties.apiKey)
        body.add("txt", txt)
        body.add("of", "json")
        body.add("lang", "en")
        body.add("txtf", "plain")
        body.add("verbose", "n")
        return body
    }
}