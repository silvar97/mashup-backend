package org.hrw.mashupbackend.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix="meaningcloud")
class MeaningcloudProperties {
    lateinit var apiKey:String
}