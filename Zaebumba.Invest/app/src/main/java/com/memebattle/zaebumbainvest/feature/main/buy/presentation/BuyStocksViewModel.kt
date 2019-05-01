package com.memebattle.zaebumbainvest.feature.main.buy.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.core.domain.BaseCallback
import com.memebattle.zaebumbainvest.core.domain.throwableMessage
import com.memebattle.zaebumbainvest.feature.main.buy.domain.BuyApiService
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.StocksRes
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainSettingsService
import com.memebattle.zaebumbainvest.feature.main.core.domain.RefreshService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

class BuyStocksViewModel : ViewModel() {
    @Inject
    lateinit var apiService: BuyApiService

    @Inject
    lateinit var refreshService: RefreshService

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var settingsService: MainSettingsService

    val compositeDisposable = CompositeDisposable()
    val stocksLiveData: MutableLiveData<StocksRes> = MutableLiveData()
    val buyResLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        App.instance.daggerComponentHelper.buyStocksComponent!!.inject(this)

    }

    @SuppressLint("CheckResult")
    fun getStocks(lastId: Long) {
        apiService
                .getStocks(settingsService.getAccessToken(), lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ stocksLiveData.value = it }) { e ->

                    if (e is HttpException) {
                        if (e.code() == 403 || e.code() == 401) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    getStocks(lastId)
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
    fun searchByName(name: String) {
        apiService.getStocksByName(settingsService.getAccessToken(), name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ stocksLiveData.value = it }) { e ->
                    if (e is HttpException) {
                        if (e.code() == 403) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    searchByName(name)
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @SuppressLint("CheckResult")
    fun buyStocks(id: Long, count: Long) {
        apiService.buyStocks(settingsService.getAccessToken(), id, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ buyResLiveData.value = it.status }) { e ->
                    if (e is HttpException) {
                        if (e.code() == 403) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    buyStocks(id, count)
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
}