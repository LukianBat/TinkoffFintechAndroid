package com.memebattle.zaebumbainvest.core.di

import com.memebattle.zaebumbainvest.core.di.core.module.ApiModule
import com.memebattle.zaebumbainvest.core.di.core.module.AppModule
import com.memebattle.zaebumbainvest.core.di.core.module.SharedPreferencesModule
//import com.memebattle.zaebumbainvest.core.di.core.module.RoomModule
import com.memebattle.zaebumbainvest.core.di.sub.auth.AuthComponent
import com.memebattle.zaebumbainvest.core.di.sub.main.MainComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, SharedPreferencesModule::class])
interface AppComponent {
    fun authComponentBuilder(): AuthComponent.Builder
    fun mainComponentBuilder(): MainComponent.Builder
}