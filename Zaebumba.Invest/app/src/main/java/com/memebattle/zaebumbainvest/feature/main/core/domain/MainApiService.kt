package com.memebattle.zaebumbainvest.feature.main.core.domain

import com.memebattle.zaebumbainvest.core.domain.BaseCallback

import com.memebattle.zaebumbainvest.feature.main.core.data.ApiMain
import com.memebattle.zaebumbainvest.feature.main.core.domain.model.RefreshReq
import com.memebattle.zaebumbainvest.feature.main.core.domain.model.RefreshRes
import io.reactivex.Single

class MainApiService(var api: ApiMain) {

    fun refreshTokens(accessToken: String, mainReq: RefreshReq, callback: BaseCallback<Single<RefreshRes>>) {
        callback.onSuccess(api.refreshToken(accessToken, mainReq))
    }

}