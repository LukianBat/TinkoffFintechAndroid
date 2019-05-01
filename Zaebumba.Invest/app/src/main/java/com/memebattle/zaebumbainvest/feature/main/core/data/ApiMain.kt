package com.memebattle.zaebumbainvest.feature.main.core.data


import com.memebattle.zaebumbainvest.feature.main.core.domain.model.RefreshReq
import com.memebattle.zaebumbainvest.feature.main.core.domain.model.RefreshRes
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiMain {

    @POST("/api/auth/refresh")
    fun refreshToken(@Header("Authorization") header: String, @Body mainReq: RefreshReq): Single<RefreshRes>

}