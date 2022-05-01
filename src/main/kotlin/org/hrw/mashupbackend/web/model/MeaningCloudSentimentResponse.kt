package org.hrw.mashupbackend.web.model


import com.fasterxml.jackson.annotation.JsonProperty

data class MeaningCloudSentimentResponse(
    @JsonProperty("agreement")
    var agreement: String?,
    @JsonProperty("confidence")
    var confidence: String?,
    @JsonProperty("irony")
    var irony: String?,
    @JsonProperty("model")
    var model: String?,
    @JsonProperty("score_tag")
    var scoreTag: String?,
    @JsonProperty("sentence_list")
    var sentenceList: List<Sentence?>?,
    @JsonProperty("sentimented_concept_list")
    var sentimentedConceptList: List<SentimentedConcept?>?,
    @JsonProperty("sentimented_entity_list")
    var sentimentedEntityList: List<Any?>?,
    @JsonProperty("status")
    var status: Status?,
    @JsonProperty("subjectivity")
    var subjectivity: String?
) {
    data class Sentence(
        @JsonProperty("agreement")
        var agreement: String?,
        @JsonProperty("bop")
        var bop: String?,
        @JsonProperty("confidence")
        var confidence: String?,
        @JsonProperty("endp")
        var endp: String?,
        @JsonProperty("inip")
        var inip: String?,
        @JsonProperty("score_tag")
        var scoreTag: String?,
        @JsonProperty("segment_list")
        var segmentList: List<Segment?>?,
        @JsonProperty("sentimented_concept_list")
        var sentimentedConceptList: List<SentimentedConcept?>?,
        @JsonProperty("sentimented_entity_list")
        var sentimentedEntityList: List<Any?>?,
        @JsonProperty("text")
        var text: String?
    ) {
        data class Segment(
            @JsonProperty("agreement")
            var agreement: String?,
            @JsonProperty("confidence")
            var confidence: String?,
            @JsonProperty("endp")
            var endp: String?,
            @JsonProperty("inip")
            var inip: String?,
            @JsonProperty("polarity_term_list")
            var polarityTermList: List<PolarityTerm?>?,
            @JsonProperty("score_tag")
            var scoreTag: String?,
            @JsonProperty("segment_type")
            var segmentType: String?,
            @JsonProperty("text")
            var text: String?
        ) {
            data class PolarityTerm(
                @JsonProperty("confidence")
                var confidence: String?,
                @JsonProperty("endp")
                var endp: String?,
                @JsonProperty("inip")
                var inip: String?,
                @JsonProperty("score_tag")
                var scoreTag: String?,
                @JsonProperty("sentimented_concept_list")
                var sentimentedConceptList: List<SentimentedConcept?>?,
                @JsonProperty("text")
                var text: String?
            ) {
                data class SentimentedConcept(
                    @JsonProperty("endp")
                    var endp: String?,
                    @JsonProperty("form")
                    var form: String?,
                    @JsonProperty("id")
                    var id: String?,
                    @JsonProperty("inip")
                    var inip: String?,
                    @JsonProperty("score_tag")
                    var scoreTag: String?,
                    @JsonProperty("type")
                    var type: String?,
                    @JsonProperty("variant")
                    var variant: String?
                )
            }
        }

        data class SentimentedConcept(
            @JsonProperty("form")
            var form: String?,
            @JsonProperty("id")
            var id: String?,
            @JsonProperty("score_tag")
            var scoreTag: String?,
            @JsonProperty("type")
            var type: String?
        )
    }

    data class SentimentedConcept(
        @JsonProperty("form")
        var form: String?,
        @JsonProperty("id")
        var id: String?,
        @JsonProperty("score_tag")
        var scoreTag: String?,
        @JsonProperty("type")
        var type: String?
    )

    data class Status(
        @JsonProperty("code")
        var code: String?,
        @JsonProperty("credits")
        var credits: String?,
        @JsonProperty("msg")
        var msg: String?,
        @JsonProperty("remaining_credits")
        var remainingCredits: String?
    )
}