package com.memebattle.zaebumbainvest.core.di.sub.main

import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.core.di.sub.buy.BuyStocksComponent

import com.memebattle.zaebumbainvest.core.di.sub.main.module.MainApiModule
import com.memebattle.zaebumbainvest.core.di.sub.main.module.MainRefreshModule
import com.memebattle.zaebumbainvest.core.di.sub.main.module.MainSettingsModule
import com.memebattle.zaebumbainvest.core.di.sub.profile.ProfileComponent
import com.memebattle.zaebumbainvest.core.di.sub.transactions.TransactionsComponent
import com.memebattle.zaebumbainvest.feature.main.fragments.account.AccountFragment

import dagger.Subcomponent

@Subcomponent(modules = [MainSettingsModule::class, MainApiModule::class, MainRefreshModule::class])
@ActivityScope
interface MainComponent {
    fun profileComponentBuilder(): ProfileComponent.Builder
    fun buyStocksComponentBuilder(): BuyStocksComponent.Builder
    fun transactionsComponentBuilder(): TransactionsComponent.Builder
    fun inject(accountFragment: AccountFragment)


    @Subcomponent.Builder
    interface Builder {
        fun build(): MainComponent
    }
}