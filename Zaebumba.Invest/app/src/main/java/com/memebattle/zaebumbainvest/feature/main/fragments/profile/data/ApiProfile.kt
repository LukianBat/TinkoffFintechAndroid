package com.memebattle.zaebumbainvest.feature.main.fragments.profile.data

import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.AccountInfo
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.SellReq
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.SellRes
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiProfile {

    @GET("/api/account/info")
    fun getProfile(@Header("Authorization") header: String): Single<AccountInfo>

    @POST("/api/transaction/sell")
    fun sellStock(@Header("Authorization") header: String, @Body sellReq: SellReq): Single<SellRes>

}