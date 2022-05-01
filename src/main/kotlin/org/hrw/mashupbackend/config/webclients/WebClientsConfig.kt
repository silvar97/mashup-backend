package org.hrw.mashupbackend.config.webclients

import org.hrw.mashupbackend.config.properties.WebClientConfigProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClientConfig(
    private val properties: WebClientConfigProperties
) {

    @Bean
    @Qualifier("wavescapeWebClient")
    fun wavescapeClient(): WebClient {
        return WebClient.builder()
            .baseUrl(properties.baseUrlWavescape)
            .build()
    }

    @Bean
    @Qualifier("blockchairWebClient")
    fun blockchairClient(): WebClient {
        return WebClient.builder()
            .baseUrl(properties.baseUrlBlockchair)
            .build()
    }

    @Bean
    @Qualifier("alphavantageWebClient")
    fun alphavantageClient(): WebClient {
        val size = 16 * 1024 * 1024
        val strategies = ExchangeStrategies.builder()
            .codecs { codecs -> codecs.defaultCodecs().maxInMemorySize(size) }
            .build()
        return WebClient.builder()
            .exchangeStrategies(strategies)
            .baseUrl(properties.baseUrlAlphavantage)
            .build()
    }

    @Bean
    @Qualifier("meaningcloudClient")
    fun meaningcloudClient(): WebClient {
        return WebClient.builder()
            .baseUrl(properties.baseUrlMeaningcloud)
            .build()
    }
}
