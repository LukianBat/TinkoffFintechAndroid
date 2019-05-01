package com.memebattle.zaebumbainvest.feature.main.fragments.transactions.data

import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.TransactionRes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiTransactions {
    @GET("/api/transaction/history")
    fun getTransactions(@Header("Authorization") header: String, @Query("itemId") lastId: Long): Single<TransactionRes>

}