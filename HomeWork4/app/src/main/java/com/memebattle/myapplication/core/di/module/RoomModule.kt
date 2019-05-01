package com.memebattle.myapplication.core.di.module

import android.content.Context
import androidx.room.Room
import com.memebattle.myapplication.core.data.AppDatabase
import com.memebattle.myapplication.core.data.NodeDao
import com.memebattle.myapplication.core.domain.interactor.NodeService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    @Provides
    @Singleton
    fun provideNodeDao(appDatabase: AppDatabase): NodeDao {
        return appDatabase.nodeDao()
    }

    @Provides
    @Singleton
    fun roomCashService(dao: NodeDao): NodeService {
        return NodeService(dao)
    }
}