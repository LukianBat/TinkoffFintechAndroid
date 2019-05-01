package com.memebattle.zaebumbainvest

import android.app.Application
import com.memebattle.zaebumbainvest.core.di.helper.DaggerComponentHelper
import com.memebattle.zaebumbainvest.core.domain.checkInternet


class App : Application() {
    lateinit var daggerComponentHelper: DaggerComponentHelper

    companion object {
        lateinit var instance: App
        fun hasNetwork(): Boolean {
            return instance.checkInternet()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        val url = getString(R.string.base_url)
        daggerComponentHelper = DaggerComponentHelper(url)
    }
}