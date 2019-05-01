package com.memebattle.zaebumbainvest.feature.auth.core.data

import com.memebattle.zaebumbainvest.feature.auth.core.domain.model.AuthReq
import com.memebattle.zaebumbainvest.feature.auth.core.domain.model.AuthRes
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiAuth {
    @POST("/api/auth/signin")
    fun signIn(@Body authReq: AuthReq): Single<AuthRes>

    @POST("/api/auth/signup")
    fun signUp(@Body authReq: AuthReq): Single<AuthRes>
}