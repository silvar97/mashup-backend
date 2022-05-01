package org.hrw.mashupbackend.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "webclient")
class WebClientConfigProperties {
    lateinit var baseUrlWavescape: String
    lateinit var baseUrlBlockchair: String
    lateinit var baseUrlAlphavantage:String
    lateinit var baseUrlMeaningcloud:String
}