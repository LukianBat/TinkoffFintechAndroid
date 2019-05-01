package com.memebattle.zaebumbainvest.feature.main.buy.domain

import com.memebattle.zaebumbainvest.feature.main.buy.data.ApiBuy
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.BuyReq
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.BuyRes
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.StocksRes
import io.reactivex.Single

class BuyApiService(var api: ApiBuy) {

    fun getStocks(accessToken: String, lastId: Long): Single<StocksRes> =
            (api.getStocks(accessToken, lastId))


    fun getStocksByName(accessToken: String, name: String): Single<StocksRes> =
            (api.getByName(accessToken, name))


    fun buyStocks(accessToken: String, stockId: Long, amount: Long): Single<BuyRes> =
            (api.buyStock(accessToken, BuyReq(stockId, amount)))

}