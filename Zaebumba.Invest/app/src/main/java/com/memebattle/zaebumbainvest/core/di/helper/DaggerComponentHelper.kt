package com.memebattle.zaebumbainvest.core.di.helper

import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.core.di.DaggerAppComponent
import com.memebattle.zaebumbainvest.core.di.core.module.ApiModule
import com.memebattle.zaebumbainvest.core.di.core.module.AppModule
import com.memebattle.zaebumbainvest.core.di.sub.auth.AuthComponent
import com.memebattle.zaebumbainvest.core.di.sub.buy.BuyStocksComponent
import com.memebattle.zaebumbainvest.core.di.sub.main.MainComponent
import com.memebattle.zaebumbainvest.core.di.sub.profile.ProfileComponent
import com.memebattle.zaebumbainvest.core.di.sub.transactions.TransactionsComponent

class DaggerComponentHelper(url: String) {

    private val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(App.instance))
            .apiModule(ApiModule(url))
            .build()

    var authComponent: AuthComponent? = null
    var mainComponent: MainComponent? = null
    var profileComponent: ProfileComponent? = null
    var buyStocksComponent: BuyStocksComponent? = null
    var transactionsComponent: TransactionsComponent? = null

    fun plusProfileComponent() {
        if (profileComponent == null && mainComponent != null) {
            profileComponent = mainComponent!!
                    .profileComponentBuilder()
                    .build()
        }
    }

    fun removeProfileComponent() {
        profileComponent = null
    }

    fun plusAuthComponent() {
        if (authComponent == null)
            authComponent = appComponent
                    .authComponentBuilder()
                    .build()
    }

    fun removeAuthComponent() {
        authComponent = null
    }

    fun plusMainComponent() {
        if (mainComponent == null)
            mainComponent = appComponent
                    .mainComponentBuilder()
                    .build()
    }

    fun removeMainComponent() {
        mainComponent = null
    }

    fun plusBuyStocksComponent() {
        if (buyStocksComponent == null && mainComponent != null) {
            buyStocksComponent = mainComponent!!
                    .buyStocksComponentBuilder()
                    .build()
        }
    }

    fun removeBuyStocksComponent() {
        buyStocksComponent = null
    }

    fun plusTransactionsComponent() {
        if (transactionsComponent == null && mainComponent != null) {
            transactionsComponent = mainComponent!!
                    .transactionsComponentBuilder()
                    .build()
        }
    }

    fun removeTransactionsComponent() {
        transactionsComponent = null
    }
}