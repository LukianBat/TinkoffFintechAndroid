package com.memebattle.zaebumbainvest.core.di.sub.buy

import com.memebattle.zaebumbainvest.core.di.core.scope.FragmentScope
import com.memebattle.zaebumbainvest.core.di.sub.buy.module.BuyApiModule
import com.memebattle.zaebumbainvest.feature.main.buy.presentation.BuyStocksViewModel
import dagger.Subcomponent

@Subcomponent(modules = [BuyApiModule::class])
@FragmentScope
interface BuyStocksComponent {
    fun inject(buyStocksViewModel: BuyStocksViewModel)
    @Subcomponent.Builder
    interface Builder {
        fun build(): BuyStocksComponent
    }
}