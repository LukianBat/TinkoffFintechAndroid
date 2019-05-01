package com.memebattle.zaebumbainvest.feature.auth.core.domain

import com.memebattle.zaebumbainvest.feature.auth.core.data.ApiAuth
import com.memebattle.zaebumbainvest.feature.auth.core.domain.model.AuthReq
import com.memebattle.zaebumbainvest.feature.auth.core.domain.model.AuthRes
import io.reactivex.Single

class AuthApiService(var api: ApiAuth) {

    fun signIn(authReq: AuthReq): Single<AuthRes> = api.signIn(authReq)


    fun signUp(authReq: AuthReq): Single<AuthRes> = api.signUp(authReq)

}