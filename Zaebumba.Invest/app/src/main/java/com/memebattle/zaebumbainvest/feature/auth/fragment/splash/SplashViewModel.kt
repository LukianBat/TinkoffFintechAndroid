package com.memebattle.zaebumbainvest.feature.auth.fragment.splash

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.feature.auth.core.domain.AuthSettingsService
import javax.inject.Inject

class SplashViewModel : ViewModel() {
    @Inject
    lateinit var settingsService: AuthSettingsService

    @Inject
    lateinit var context: Context

    val authCheckData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        App.instance.daggerComponentHelper.authComponent!!.inject(this)
    }

    fun authDataCheck() {
        authCheckData.value = settingsService.getAccessToken() != context.getString(R.string.token_error)
    }
}