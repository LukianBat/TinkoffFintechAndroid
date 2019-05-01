package com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model

data class AccountInfo(val name: String, val balance: Double, val stocks: ArrayList<Stock>?)

data class Stock(val id: Long,
                 val code: String,
                 val name: String,
                 val iconUrl: String?,
                 val price: Double,
                 val priceDelta: Double,
                 val count: Long)