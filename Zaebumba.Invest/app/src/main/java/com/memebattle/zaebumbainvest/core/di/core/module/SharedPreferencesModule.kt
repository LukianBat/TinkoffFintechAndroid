package com.memebattle.zaebumbainvest.core.di.core.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    companion object {
        const val NAME = "tokens"
    }
}