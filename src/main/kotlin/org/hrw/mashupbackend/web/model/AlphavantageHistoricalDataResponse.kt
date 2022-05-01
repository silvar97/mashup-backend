package  org.hrw.mashupbackend.web.model

import com.fasterxml.jackson.annotation.JsonProperty


data class AlphavantageHistoricalDataResponse(
    @JsonProperty("Meta Data")
    val metaData: MetaData,
    @JsonProperty("Time Series (Digital Currency Daily)")
    val timeSeriesDigitalCurrencyDaily: Map<String, TimeSeriesDigitalCurrencyDaily> = mutableMapOf()
)

data class MetaData(
    @JsonProperty("1. Information")
    val information: String,

    @JsonProperty("2. Digital Currency Code")
    val currencyCode: String,

    @JsonProperty("3. Digital Currency Name")
    val currencyName: String,

    @JsonProperty("4. Market Code")
    val marketCode: String,

    @JsonProperty("5. Market Name")
    val the5MarketName: String,

    @JsonProperty("6. Last Refreshed")
    val lastRefreshed: String,

    @JsonProperty("7. Time Zone")
    val timeZone: String
)

data class TimeSeriesDigitalCurrencyDaily(
    @JsonProperty("1a. open (EUR)")
    val openInEuro: String,

    @JsonProperty("1b. open (USD)")
    val openInUsd: String,

    @JsonProperty("2a. high (EUR)")
    val highInEuro: String,

    @JsonProperty("2b. high (USD)")
    val highInUsd: String,

    @JsonProperty("3a. low (EUR)")
    val lowInEuro: String,

    @JsonProperty("3b. low (USD)")
    val lowInUsd: String,

    @JsonProperty("4a. close (EUR)")
    val closeInEuro: String,

    @JsonProperty("4b. close (USD)")
    val closeInUsd: String,

    @JsonProperty("5. volume")
    val volume: String,

    @JsonProperty("6. market cap (USD)")
    val marketCapUsd: String
)
