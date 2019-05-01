package com.memebattle.zaebumbainvest.core.di.sub.transactions.module

import com.memebattle.zaebumbainvest.core.di.core.scope.FragmentScope
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.data.ApiTransactions
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.TransactionsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class TransactionsApiModule {

    @FragmentScope
    @Provides
    fun provideTransactionsApiService(@Named("refresh") retrofit: Retrofit): TransactionsApiService =
            TransactionsApiService(retrofit.create(ApiTransactions::class.java))


}