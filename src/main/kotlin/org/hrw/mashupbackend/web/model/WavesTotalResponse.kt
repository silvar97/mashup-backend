package org.hrw.mashupbackend.web.model


import com.fasterxml.jackson.annotation.JsonProperty

data class WavesTotalResponse(
    @JsonProperty("assetsCap")
    val assetsCap: AssetsCap,
    @JsonProperty("gatewaysCap")
    val gatewaysCap: GatewaysCap,
    @JsonProperty("24hVol")
    val hVol: HVol,
    @JsonProperty("totalCap")
    val totalCap: TotalCap,
    @JsonProperty("trades")
    val trades: Int
) {
    data class AssetsCap(
        @JsonProperty("btc")
        val btc: Double,
        @JsonProperty("eth")
        val eth: Double,
        @JsonProperty("eurn")
        val eurn: Double,
        @JsonProperty("usd-n")
        val usdN: Double,
        @JsonProperty("waves")
        val waves: Double
    )

    data class GatewaysCap(
        @JsonProperty("btc")
        val btc: Double,
        @JsonProperty("eth")
        val eth: Double,
        @JsonProperty("eurn")
        val eurn: Double,
        @JsonProperty("usd-n")
        val usdN: Double,
        @JsonProperty("waves")
        val waves: Double
    )

    data class HVol(
        @JsonProperty("btc")
        val btc: Double,
        @JsonProperty("eth")
        val eth: Double,
        @JsonProperty("eurn")
        val eurn: Double,
        @JsonProperty("usd-n")
        val usdN: Double,
        @JsonProperty("waves")
        val waves: Double
    )

    data class TotalCap(
        @JsonProperty("btc")
        val btc: Double,
        @JsonProperty("cnyn")
        val cnyn: Double,
        @JsonProperty("eth")
        val eth: Double,
        @JsonProperty("eurn")
        val eurn: Double,
        @JsonProperty("usd-n")
        val usdN: Double,
        @JsonProperty("waves")
        val waves: Double
    )
}