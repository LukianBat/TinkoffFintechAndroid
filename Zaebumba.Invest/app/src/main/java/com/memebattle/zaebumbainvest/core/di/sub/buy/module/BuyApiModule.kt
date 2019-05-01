package com.memebattle.zaebumbainvest.core.di.sub.buy.module

import com.memebattle.zaebumbainvest.core.di.core.scope.FragmentScope
import com.memebattle.zaebumbainvest.feature.main.buy.data.ApiBuy
import com.memebattle.zaebumbainvest.feature.main.buy.domain.BuyApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class BuyApiModule {

    @FragmentScope
    @Provides
    fun provideBuyApiService(@Named("refresh") retrofit: Retrofit): BuyApiService =
            BuyApiService(retrofit.create(ApiBuy::class.java))

}