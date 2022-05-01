package org.hrw.mashupbackend.api

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.DgsSubscription
import org.hrw.mashup.backend.types.News
import org.hrw.mashupbackend.service.BlockchairService
import org.reactivestreams.Publisher

@DgsComponent
class NewsDataFetcher(val blockchairService: BlockchairService) {
    @DgsQuery
    fun getNewsForWaves(): Publisher<List<News>> {
        return blockchairService.getNewsForWaves()
    }

    @DgsQuery
    fun getNewsForBtc(): Publisher<List<News>> {
        return blockchairService.getNewsForBtc()
    }

    @DgsQuery
    fun getNewsForEth(): Publisher<List<News>> {
        return blockchairService.getNewsForEth()
    }

    @DgsSubscription
    fun getNewsForWavesSubscription(): Publisher<List<News>> {
        return blockchairService.getNewsForWaves()
    }

    @DgsSubscription
    fun getNewsForBtcSubscription(): Publisher<List<News>> {
        return blockchairService.getNewsForBtc()
    }

    @DgsSubscription
    fun getNewsForEthSubscription(): Publisher<List<News>> {
        return blockchairService.getNewsForEth()
    }
}