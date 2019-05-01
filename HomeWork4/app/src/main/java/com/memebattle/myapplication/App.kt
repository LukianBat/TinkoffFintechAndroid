package com.memebattle.myapplication

import android.app.Application
import com.memebattle.myapplication.core.di.AppComponent
import com.memebattle.myapplication.core.di.DaggerAppComponent
import com.memebattle.myapplication.core.di.module.AppModule
import com.memebattle.myapplication.core.di.module.RoomModule

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerAppComponent.builder().appModule(AppModule(applicationContext))
                .roomModule(RoomModule())
                .build()
    }
}