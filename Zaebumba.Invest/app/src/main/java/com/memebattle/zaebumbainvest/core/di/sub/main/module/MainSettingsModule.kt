package com.memebattle.zaebumbainvest.core.di.sub.main.module

import android.content.Context
import android.content.SharedPreferences
import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainSettingsService
import dagger.Module
import dagger.Provides

@Module
class MainSettingsModule {
    @ActivityScope
    @Provides
    fun provideMainSettingsService(sharedPreferences: SharedPreferences, context: Context):
            MainSettingsService = MainSettingsService(sharedPreferences, context)

}