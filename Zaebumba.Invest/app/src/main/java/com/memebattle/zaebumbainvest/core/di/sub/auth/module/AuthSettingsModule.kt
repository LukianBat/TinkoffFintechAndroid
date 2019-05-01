package com.memebattle.zaebumbainvest.core.di.sub.auth.module


import android.content.Context
import android.content.SharedPreferences
import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.feature.auth.core.domain.AuthSettingsService
import dagger.Module
import dagger.Provides

@Module
class AuthSettingsModule {
    @ActivityScope
    @Provides
    fun provideAuthSettingsService(sharedPreferences: SharedPreferences, context: Context): AuthSettingsService =
            AuthSettingsService(sharedPreferences, context)

}