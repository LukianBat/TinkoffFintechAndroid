package com.memebattle.zaebumbainvest.feature.auth.fragment.signup.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.core.domain.throwableMessage
import com.memebattle.zaebumbainvest.feature.auth.core.domain.AuthApiService
import com.memebattle.zaebumbainvest.feature.auth.core.domain.AuthSettingsService
import com.memebattle.zaebumbainvest.feature.auth.core.domain.model.AuthReq
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignUpViewModel : ViewModel() {
    @Inject
    lateinit var settingsService: AuthSettingsService

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var apiService: AuthApiService

    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val authLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        App.instance.daggerComponentHelper.authComponent!!.inject(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun signUp(login: String, pass: String) {
        compositeDisposable.add(apiService.signUp(AuthReq(login, pass))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            settingsService.setTokens(result.refreshToken, result.accessToken)
                            authLiveData.value = true
                        },
                        { error ->
                            errorLiveData.value = error.throwableMessage(context)
                        }
                )
        )
    }
}