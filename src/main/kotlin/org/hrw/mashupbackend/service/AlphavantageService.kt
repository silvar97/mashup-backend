package org.hrw.mashupbackend.service

import org.hrw.mashup.backend.types.Currency
import org.hrw.mashup.backend.types.HistoricalData
import org.hrw.mashup.backend.types.PriceData
import org.hrw.mashupbackend.config.properties.AlphaVantageProperties
import org.hrw.mashupbackend.web.AlphavantageRequest
import org.hrw.mashupbackend.web.model.AlphavantageHistoricalDataResponse
import org.hrw.mashupbackend.web.model.TimeSeriesDigitalCurrencyDaily
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Service
class AlphavantageService(
    val alphavantageRequest: AlphavantageRequest,
    val alphaVantageProperties: AlphaVantageProperties
) {
    init {
        loadDataHistoricalData()
    }

    enum class CryptoNames {
        WAVES,
        BTC,
        ETH
    }

    enum class AlphavantageCurrencyEnum {
        USD,
        EUR
    }

    private val data: MutableMap<CryptoNames, AlphavantageHistoricalDataResponse> =
        ConcurrentHashMap()

    @Scheduled(cron = "\${scheduling.time.historicaldata}")
    private fun loadDataHistoricalData() {
        //function=DIGITAL_CURRENCY_DAILY&symbol=WAVES&market=EUR&apikey=JHO5YSQJRM2ZBYGO
        val queryParamForWaves = createQueryParamForHistoricalData(
            CryptoNames.WAVES,
            AlphavantageCurrencyEnum.EUR,
            alphaVantageProperties.apiKey
        )
        alphavantageRequest.requestData(queryParamForWaves)
            .doOnError { WavescapeService.logger.error("something went wrong while loading asset data from wavescape") }
            .subscribe {
                data[CryptoNames.WAVES] = it
            }
        val queryParamForBTC = createQueryParamForHistoricalData(
            CryptoNames.BTC,
            AlphavantageCurrencyEnum.EUR,
            alphaVantageProperties.apiKey
        )
        alphavantageRequest.requestData(queryParamForBTC)
            .doOnError { WavescapeService.logger.error("something went wrong while loading asset data from BTC") }
            .subscribe {
                data[CryptoNames.BTC] = it
            }
        val queryParamForETH = createQueryParamForHistoricalData(
            CryptoNames.ETH,
            AlphavantageCurrencyEnum.EUR,
            alphaVantageProperties.apiKey
        )
        alphavantageRequest.requestData(queryParamForETH)
            .doOnError { WavescapeService.logger.error("something went wrong while loading asset data from ETH") }
            .subscribe {
                data[CryptoNames.ETH] = it
            }

    }

    fun getHistoricalDataForWaves(currency: Currency): Mono<HistoricalData> {
        return Mono.justOrEmpty(data[CryptoNames.WAVES])
            .map {
                HistoricalData(
                    name = it.metaData.currencyName,
                    lastRefreshed = it.metaData.lastRefreshed,
                    priceData = convertPriceData(currency, it.timeSeriesDigitalCurrencyDaily)
                )
            }
    }

    fun getHistoricalDataForBTC(currency: Currency): Mono<HistoricalData> {
        return Mono.justOrEmpty(data[CryptoNames.BTC])
            .map {
                HistoricalData(
                    name = it.metaData.currencyName,
                    lastRefreshed = it.metaData.lastRefreshed,
                    priceData = convertPriceData(currency, it.timeSeriesDigitalCurrencyDaily)
                )
            }
    }

    fun getHistoricalDataForETH(currency: Currency): Mono<HistoricalData> {
        return Mono.justOrEmpty(data[CryptoNames.ETH])
            .map {
                HistoricalData(
                    name = it.metaData.currencyName,
                    lastRefreshed = it.metaData.lastRefreshed,
                    priceData = convertPriceData(currency, it.timeSeriesDigitalCurrencyDaily)
                )
            }
    }

    private fun convertPriceData(
        currency: Currency,
        dailyPricesMap: Map<String, TimeSeriesDigitalCurrencyDaily>
    ): List<PriceData> {
        return dailyPricesMap.entries.map {
            PriceData(
                price = getPrice(currency, it.value),
                date = it.key,
                volume = it.value.volume
            )
        }.take(100)
    }

    private fun getPrice(currency: Currency, timeSeries: TimeSeriesDigitalCurrencyDaily): String {
        return when (currency) {
            Currency.EURO -> timeSeries.highInEuro
            else -> timeSeries.highInUsd
        }
    }

    //function=DIGITAL_CURRENCY_DAILY&symbol=WAVES&market=EUR&apikey=JHO5YSQJRM2ZBYGO
    companion object {
        fun createQueryParamForHistoricalData(
            cryptoname: CryptoNames,
            currency: AlphavantageCurrencyEnum,
            apiKey: String
        ): LinkedMultiValueMap<String, String> {
            val queryParam = LinkedMultiValueMap<String, String>()
            queryParam.add("function", "DIGITAL_CURRENCY_DAILY")
            queryParam.add("symbol", cryptoname.name)
            queryParam.add("market", currency.name)
            queryParam.add("interval", "5min")
            queryParam.add("apikey", apiKey)
            return queryParam
        }
    }
}