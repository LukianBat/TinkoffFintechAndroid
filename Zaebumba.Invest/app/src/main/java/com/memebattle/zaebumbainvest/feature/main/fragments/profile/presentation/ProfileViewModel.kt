package com.memebattle.zaebumbainvest.feature.main.fragments.profile.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.core.domain.BaseCallback
import com.memebattle.zaebumbainvest.core.domain.throwableMessage
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainSettingsService
import com.memebattle.zaebumbainvest.feature.main.core.domain.RefreshService
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.ProfileApiService
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.AccountInfo
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.SellRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

class ProfileViewModel : ViewModel() {

    @field:[Inject Named("refresh")]
    lateinit var apiService: ProfileApiService

    @field:[Inject Named("cash")]
    lateinit var apiCashService: ProfileApiService

    @Inject
    lateinit var refreshService: RefreshService

    @Inject
    lateinit var context: Context
    @Inject
    lateinit var settingsService: MainSettingsService


    val sellResLiveData: MutableLiveData<SellRes> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val profileLiveData: MutableLiveData<AccountInfo> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        App.instance.daggerComponentHelper.profileComponent!!.inject(this)
    }

    @SuppressLint("CheckResult")
    fun refreshProfile() {
        apiService.getProfile(settingsService.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ profileLiveData.value = it }) { e ->
                    if (e is HttpException) {
                        if (e.code() == 403 || e.code() == 401) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    refreshProfile()
                                }

                            })
                        } else {
                            errorLiveData.value = e.throwableMessage(context)
                        }
                    } else {
                        errorLiveData.value = e.throwableMessage(context)
                    }

                }

    }

    @SuppressLint("CheckResult")
    fun getCashProfile() {
        apiCashService.getProfile(settingsService.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ profileLiveData.value = it }) { e ->
                    if (e is HttpException) {
                        if (e.code() == 403 || e.code() == 401) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    getCashProfile()
                                }

                            })
                        } else {
                            errorLiveData.value = e.throwableMessage(context)
                        }
                    } else {
                        errorLiveData.value = e.throwableMessage(context)
                    }

                }

    }

    @SuppressLint("CheckResult")
    fun sellStock(stockId: Long, amount: Long) {
        apiService.sellStock(settingsService.getAccessToken(), stockId, amount)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sellResLiveData.value = it }, { e ->
                    if (e is HttpException) {
                        if (e.code() == 403 || e.code() == 401) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    sellStock(stockId, amount)
                                }
                            })
                        } else {
                            errorLiveData.value = e.throwableMessage(context)
                        }
                    } else {
                        errorLiveData.value = e.throwableMessage(context)
                    }
                })

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}