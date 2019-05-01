package com.memebattle.zaebumbainvest.core.di.sub.main.module


import android.content.Context
import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainApiService
import com.memebattle.zaebumbainvest.feature.main.core.domain.MainSettingsService
import com.memebattle.zaebumbainvest.feature.main.core.domain.RefreshService
import dagger.Module
import dagger.Provides

@Module
class MainRefreshModule {

    @ActivityScope
    @Provides
    fun provideMainRefresh(mainSettingsService: MainSettingsService,
                           mainApiService: MainApiService,
                           context: Context): RefreshService =
            RefreshService(mainSettingsService, mainApiService, context)

}