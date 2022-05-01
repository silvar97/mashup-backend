package org.hrw.mashupbackend.service

import org.hrw.mashupbackend.service.model.SentimentAnalysisEnum
import org.hrw.mashupbackend.web.MeaningCloudRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MeaningCloudService(val meaningCloudRequest: MeaningCloudRequest) {

    fun getSentimentAnalysisFromText(txt: String): Mono<SentimentAnalysisEnum> {
        return meaningCloudRequest.requestSentimentAnalysisFor(txt).map {
            if (it.confidence?.toInt()!! > confidenceThreshHold) {
                when (it.agreement) {
                    "AGREEMENT" -> SentimentAnalysisEnum.GOOD
                    "DISAGREEMENT" -> SentimentAnalysisEnum.BAD
                    else -> SentimentAnalysisEnum.NEUTRAL
                }
            }
            SentimentAnalysisEnum.NEUTRAL
        }
    }

    companion object {
        const val confidenceThreshHold = 70
    }
}