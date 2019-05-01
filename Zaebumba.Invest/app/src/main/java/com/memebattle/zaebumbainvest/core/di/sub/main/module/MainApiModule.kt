package com.memebattle.zaebumbainvest.core.di.sub.main.module

import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.feature.main.core.data.ApiMain
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class MainApiModule {

    @ActivityScope
    @Provides
    fun provideMainApiService(@Named("refresh") retrofit: Retrofit): MainApiService =
            MainApiService(retrofit.create(ApiMain::class.java))

}