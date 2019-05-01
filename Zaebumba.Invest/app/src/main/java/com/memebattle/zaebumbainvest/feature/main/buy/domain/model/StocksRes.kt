package com.memebattle.zaebumbainvest.feature.main.buy.domain.model

data class StocksRes(val nextItemId: Long?, val prevItemId: Long?, val items: ArrayList<Stock>?)

data class Stock(val id: Long, val code: String, val name: String, val iconUrl: String?, val price: Double, val priceDelta: Double)