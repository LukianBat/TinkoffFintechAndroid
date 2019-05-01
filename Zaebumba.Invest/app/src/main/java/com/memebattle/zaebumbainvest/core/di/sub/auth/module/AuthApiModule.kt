package com.memebattle.zaebumbainvest.core.di.sub.auth.module

import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.feature.auth.core.data.ApiAuth
import com.memebattle.zaebumbainvest.feature.auth.core.domain.AuthApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class AuthApiModule {

    @ActivityScope
    @Provides
    fun provideAuthApiService(@Named("refresh") retrofit: Retrofit): AuthApiService =
            AuthApiService(retrofit.create(ApiAuth::class.java))

}