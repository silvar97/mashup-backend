package org.hrw.mashupbackend.web.model


import com.fasterxml.jackson.annotation.JsonProperty

class WavesAssetsResponse : ArrayList<WavesAssetsResponse.WavesAssetsResponseItem>() {
    data class WavesAssetsResponseItem(
        @JsonProperty("circulating")
        val circulating: Long,
        @JsonProperty("data")
        val `data`: Data,
        @JsonProperty("24h_vol_btc")
        val hVolBtc: Double,
        @JsonProperty("24h_vol_eth")
        val hVolEth: Double,
        @JsonProperty("24h_vol_eurn")
        val hVolEurn: Double,
        @JsonProperty("24h_vol_usd-n")
        val hVolUsdN: Double,
        @JsonProperty("24h_vol_waves")
        val hVolWaves: Double,
        @JsonProperty("id")
        val id: String,
//        @JsonProperty("main")
//        val main: String,
        @JsonProperty("name")
        val name: String,
        @JsonProperty("precision")
        val precision: Int,
        @JsonProperty("shortcode")
        val shortcode: String,
//        @JsonProperty("symbol")
//        val symbol: String,
        @JsonProperty("total_staked")
        val totalStaked: Double,
        @JsonProperty("totalSupply")
        val totalSupply: Long,
        @JsonProperty("trades")
        val trades: Int,
    ) {
        data class Data(
            @JsonProperty("firstPrice_btc")
            val firstPriceBtc: Double,
            @JsonProperty("firstPrice_eth")
            val firstPriceEth: Double,
            @JsonProperty("firstPrice_eurn")
            val firstPriceEurn: Double,
            @JsonProperty("firstPrice_usd-n")
            val firstPriceUsdN: Double,
            @JsonProperty("lastPrice_btc")
            val lastPriceBtc: Double,
            @JsonProperty("lastPrice_eth")
            val lastPriceEth: Double,
            @JsonProperty("lastPrice_eurn")
            val lastPriceEurn: Double,
            @JsonProperty("lastPrice_usd-n")
            val lastPriceUsdN: Double,
        )
    }
}