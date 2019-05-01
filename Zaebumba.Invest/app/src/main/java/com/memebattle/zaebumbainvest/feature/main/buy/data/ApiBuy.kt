package com.memebattle.zaebumbainvest.feature.main.buy.data

import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.BuyReq
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.BuyRes
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.StocksRes
import io.reactivex.Single
import retrofit2.http.*

interface ApiBuy {

    @GET("/api/stocks")
    fun getStocks(@Header("Authorization") header: String, @Query("itemId") lastId: Long): Single<StocksRes>

    @GET("/api/stocks")
    fun getByName(@Header("Authorization") header: String, @Query("search") name: String): Single<StocksRes>

    @POST("/api/transaction/buy")
    fun buyStock(@Header("Authorization") header: String, @Body buyReq: BuyReq): Single<BuyRes>

}