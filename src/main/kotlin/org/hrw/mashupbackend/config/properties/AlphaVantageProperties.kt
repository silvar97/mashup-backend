package org.hrw.mashupbackend.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "alphavantage")
class AlphaVantageProperties {
    lateinit var apiKey: String
}