package com.memebattle.zaebumbainvest.feature.main.fragments.account

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Named

class AccViewModel : ViewModel() {
    @field:[Inject Named("cash")]
    lateinit var apiCashService: ProfileApiService
    @Inject
    lateinit var settingsService: MainSettingsService

    @Inject
    lateinit var refreshService: RefreshService

    @Inject
    lateinit var context: Context

    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val profileLiveData: MutableLiveData<AccountInfo> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        App.instance.daggerComponentHelper.profileComponent!!.inject(this)
    }

    @SuppressLint("CheckResult")
    fun getProfile() {
        apiCashService.getProfile(settingsService.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    compositeDisposable.add(it)
                }
                .subscribe({ profileLiveData.value = it }) { e ->
                    if (e is HttpException) {
                        if (e.code() == 403 || e.code()==401) {
                            refreshService.refreshToken(object : BaseCallback<String> {
                                override fun onSuccess(result: String) {
                                    getProfile()
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

    fun clearToken() {
        settingsService.clearTokens()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}