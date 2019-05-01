package com.memebattle.zaebumbainvest.core.di.core.module


import com.memebattle.zaebumbainvest.core.domain.provideCache
import com.memebattle.zaebumbainvest.core.domain.provideCacheInterceptor
import com.memebattle.zaebumbainvest.core.domain.provideOfflineCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule(private val url: String) {

    @Singleton
    @Provides
    @Named("refreshClient")
    fun provideRefreshClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    @Named("cashClient")
    fun provideCashClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .cache(provideCache())
            .build()

    @Singleton
    @Provides
    @Named("refresh")
    fun provideRefreshRetrofit(@Named("refreshClient") okHttpClient: OkHttpClient, builder: Retrofit.Builder): Retrofit =
            builder
                    .client(okHttpClient)
                    .build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
            Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    @Singleton
    @Provides
    @Named("cash")
    fun provideCashRetrofit(@Named("cashClient") okHttpClient: OkHttpClient, builder: Retrofit.Builder): Retrofit =
            builder
                    .client(okHttpClient)
                    .build()

}