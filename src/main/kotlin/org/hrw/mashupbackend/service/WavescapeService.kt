package org.hrw.mashupbackend.service

import org.hrw.mashup.backend.types.Asset
import org.hrw.mashup.backend.types.Currency
import org.hrw.mashup.backend.types.Total
import org.hrw.mashupbackend.web.WavescapeRequests
import org.hrw.mashupbackend.web.model.WavesAssetsResponse
import org.hrw.mashupbackend.web.model.WavesTotalResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Service
class WavescapeService(private val wavescapeRequest: WavescapeRequests) {

    private enum class Attributes {
        Assets,
        Totals
    }

    enum class FilterCrypto {
        Waves,
        Bitcoin,
        Ethereum
    }

    private val data: MutableMap<Attributes, Any?> = ConcurrentHashMap()

    @Scheduled(cron = "\${scheduling.time}")
    fun loadDataFromWavescape() {
        wavescapeRequest.requestAssetsData()
            .doOnError { logger.error("something went wrong while loading asset data from wavescape") }
            .subscribe {
                data[Attributes.Assets] = it
            }
        wavescapeRequest.requestTotals()
            .doOnError { logger.error("something went wrong while loading total data from wavescape") }
            .subscribe {
                data[Attributes.Totals] = it
            }
    }

    fun getAssetsFromWavescape(currency: Currency): Mono<List<Asset>> {
        val assetResponseData: WavesAssetsResponse? = data[Attributes.Assets] as? WavesAssetsResponse
        return Mono.justOrEmpty(assetResponseData)
            .map { convertWavesAssetsResponseToAsset(it, currency) }
            .flatMapIterable { it }
            .filter { asset -> filterOutCryptos(asset) }
            .collectList()
    }

    fun getTotalsFromWavesecape(currency: Currency): Mono<Total> {
        val totalResponseDate: WavesTotalResponse? = data[Attributes.Totals] as? WavesTotalResponse
        return Mono.justOrEmpty(totalResponseDate)
            .map {
                convertWavesTotalResponseToTotal(it, currency)
            }
    }

    private fun convertWavesTotalResponseToTotal(wavesTotalResponse: WavesTotalResponse, currency: Currency): Total {
        return Total(
            totalCap = getTotalCapByCurrency(wavesTotalResponse, currency).toString(),
            gateways = getGatewayByCurrency(wavesTotalResponse, currency).toString(),
            assets = getAssetCapByCurrency(wavesTotalResponse, currency).toString(),
            vol24H = get24HVolumeByCurrency(wavesTotalResponse, currency).toString(),
            trades24H = wavesTotalResponse.trades.toString()
        )
    }

    private fun convertWavesAssetsResponseToAsset(
        wavesAssetsResponse: WavesAssetsResponse,
        currency: Currency
    ): List<Asset> {
        return wavesAssetsResponse.map { asset ->
            Asset(
                id = asset.id,
                name = asset.name,
                shortCode = asset.shortcode,
                totalSupply = asset.totalSupply.toString(),
                trades = asset.trades.toString(),
                volume = getVolume(asset, currency).toString(),
                price = calculatePrice(asset, currency).toString(),
                change = String.format("%.2f", calculateChange(asset, currency))
            )
        }
    }

    private fun get24HVolumeByCurrency(totalResponse: WavesTotalResponse, currency: Currency): Double {
        return when (currency) {
            Currency.EURO -> totalResponse.hVol.eurn
            Currency.USD -> totalResponse.hVol.usdN
            Currency.ETH -> totalResponse.hVol.eth
            Currency.BTC -> totalResponse.hVol.btc
        }
    }

    private fun getAssetCapByCurrency(totalResponse: WavesTotalResponse, currency: Currency): Double {
        return when (currency) {
            Currency.EURO -> totalResponse.assetsCap.eurn
            Currency.USD -> totalResponse.assetsCap.usdN
            Currency.ETH -> totalResponse.assetsCap.eth
            Currency.BTC -> totalResponse.assetsCap.btc
        }
    }

    private fun getGatewayByCurrency(totalResponse: WavesTotalResponse, currency: Currency): Double {
        return when (currency) {
            Currency.EURO -> totalResponse.gatewaysCap.eurn
            Currency.USD -> totalResponse.gatewaysCap.usdN
            Currency.ETH -> totalResponse.gatewaysCap.eth
            Currency.BTC -> totalResponse.gatewaysCap.btc
        }
    }

    private fun getTotalCapByCurrency(totalResponse: WavesTotalResponse, currency: Currency): Double {
        return when (currency) {
            Currency.EURO -> totalResponse.totalCap.eurn
            Currency.USD -> totalResponse.totalCap.usdN
            Currency.ETH -> totalResponse.totalCap.eth
            Currency.BTC -> totalResponse.totalCap.btc
        }
    }

    private fun filterOutCryptos(asset: Asset): Boolean {
        return FilterCrypto.values().any { asset.name == it.name }
    }

    private fun calculateChange(asset: WavesAssetsResponse.WavesAssetsResponseItem, currency: Currency): Double {
        val change: Double = when (currency) {
            Currency.EURO -> (-1) * (100 - (asset.data.lastPriceEurn / asset.data.firstPriceEurn) * 100)
            Currency.USD -> (-1) * (100 - (asset.data.lastPriceUsdN / asset.data.firstPriceUsdN) * 100)
            Currency.ETH -> (-1) * (100 - (asset.data.lastPriceEth / asset.data.firstPriceEth) * 100)
            Currency.BTC -> (-1) * (100 - (asset.data.lastPriceBtc / asset.data.firstPriceBtc) * 100)
        }
        return change//((change * 100).roundToInt() / 100).toDouble() //(-1) * (100 - (asset['data']['lastPrice_'+cur.toLowerCase()] / asset['data']['firstPrice_'+cur.toLowerCase()]) * 100);
    }


    private fun calculatePrice(asset: WavesAssetsResponse.WavesAssetsResponseItem, currency: Currency): Double {
        return when (currency) {
            Currency.EURO -> asset.data.lastPriceEurn
            Currency.USD -> asset.data.lastPriceUsdN
            Currency.ETH -> asset.data.lastPriceEth
            Currency.BTC -> asset.data.lastPriceBtc
        }
    }

    private fun getVolume(asset: WavesAssetsResponse.WavesAssetsResponseItem, currency: Currency): Double {
        return when (currency) {
            Currency.EURO -> asset.hVolEurn
            Currency.USD -> asset.hVolUsdN
            Currency.ETH -> asset.hVolEth
            Currency.BTC -> asset.hVolBtc
        }
    }

    companion object {
        var logger: Logger = LoggerFactory.getLogger(WavescapeService::class.java)
    }
}