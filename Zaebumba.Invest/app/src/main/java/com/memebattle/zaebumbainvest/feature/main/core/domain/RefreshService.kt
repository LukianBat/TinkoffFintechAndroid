package com.memebattle.zaebumbainvest.feature.main.core.domain

import android.content.Context
import com.memebattle.zaebumbainvest.core.domain.BaseCallback
import com.memebattle.zaebumbainvest.feature.main.core.domain.model.RefreshReq
import com.memebattle.zaebumbainvest.feature.main.core.domain.model.RefreshRes
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.memebattle.zaebumbainvest.R

class RefreshService(private val mainSettingsService: MainSettingsService, private val mainApiService: MainApiService, val context: Context) {
    fun refreshToken(callback: BaseCallback<String>){
        mainApiService.refreshTokens(mainSettingsService.getAccessToken(), RefreshReq(mainSettingsService.getRefreshToken()),object : BaseCallback<Single<RefreshRes>>{
            override fun onSuccess(result: Single<RefreshRes>) {
                result.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : SingleObserver<RefreshRes> {
                            override fun onSuccess(t: RefreshRes) {
                                mainSettingsService.setTokens(t.refreshToken, t.accessToken)
                                callback.onSuccess(t.accessToken)
                            }
                            override fun onSubscribe(d: Disposable) {

                            }

                            override fun onError(e: Throwable) {
                                callback.onSuccess(context.getString(R.string.token_error))
                            }
                        })
            }
        })
    }
}