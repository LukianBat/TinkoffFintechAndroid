package com.memebattle.myapplication.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.memebattle.myapplication.core.domain.model.NodeEntity

@Database(entities = [NodeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}