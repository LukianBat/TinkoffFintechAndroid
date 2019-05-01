package com.memebattle.zaebumbainvest.core.di.sub.transactions

import com.memebattle.zaebumbainvest.core.di.core.scope.FragmentScope
import com.memebattle.zaebumbainvest.core.di.sub.transactions.module.TransactionsApiModule
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.presentation.TransactionsViewModel
import dagger.Subcomponent

@Subcomponent(modules = [TransactionsApiModule::class])
@FragmentScope
interface TransactionsComponent {
    fun inject(profileViewModel: TransactionsViewModel)
    @Subcomponent.Builder
    interface Builder {
        fun build(): TransactionsComponent
    }
}