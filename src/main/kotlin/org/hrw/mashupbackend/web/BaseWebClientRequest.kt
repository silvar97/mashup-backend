package org.hrw.mashupbackend.web

import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.client.WebClient


abstract class BaseWebClientRequest(internal val webclient: WebClient) {

    internal fun emptyPathParamMap(): MutableMap<String, Any> {
        return mutableMapOf()
    }

    internal fun emptyQueryParamMap(): LinkedMultiValueMap<String, String> {
        return LinkedMultiValueMap()
    }

}
