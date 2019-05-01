package com.memebattle.zaebumbainvest.feature.main.fragments.transactions.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.core.domain.BaseCallback
import com.memebattle.zaebumbainvest.core.domain.throwableMessage
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainSettingsService
import com.memebattle.zaebumbainvest.feature.main.core.domain.RefreshService
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.TransactionsApiService
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.TransactionRes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

class TransactionsViewModel : ViewModel() {
    @Inject
    lateinit var apiService: TransactionsApiService

    @Inject
    lateinit var refreshService: RefreshService

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var settingsService: MainSettingsService

    val transactionsLiveData: MutableLiveData<TransactionRes> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        App.instance.daggerComponentHelper.transactionsComponent!!.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @SuppressLint("CheckResult")
    fun getTransactions(lastId: Long) {
        apiService.getTransactions(settingsService.getAccessToken(), lastId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ transactionsLiveData.value = it }) { e ->
                    if (e is HttpException) {
                        if (e.code() == 403 || e.code() == 401) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    getTransactions(lastId)
                                }

                            })
                        } else {
                            Log.i("TAG", e.message)
                            errorLiveData.value = e.throwableMessage(context)
                        }
                    } else {
                        Log.i("TAG", e.message)
                        errorLiveData.value = e.throwableMessage(context)
                    }

                }
    }
}