package org.hrw.mashupbackend.web.model


import com.fasterxml.jackson.annotation.JsonProperty

data class BlockchairNewsResponse(
    @JsonProperty("data")
    var data: List<Data?>?
) {
    data class Data(
        @JsonProperty("description")
        var description: String?,
        @JsonProperty("file")
        var file: String?,
        @JsonProperty("hash")
        var hash: String?,
        @JsonProperty("language")
        var language: String?,
        @JsonProperty("link")
        var link: String?,
        @JsonProperty("link_amp")
        var linkAmp: Any?,
        @JsonProperty("link_iframable")
        var linkIframable: Boolean?,
        @JsonProperty("permalink")
        var permalink: String?,
        @JsonProperty("source")
        var source: String?,
        @JsonProperty("tags")
        var tags: String?,
        @JsonProperty("time")
        var time: String?,
        @JsonProperty("title")
        var title: String?
    )
}