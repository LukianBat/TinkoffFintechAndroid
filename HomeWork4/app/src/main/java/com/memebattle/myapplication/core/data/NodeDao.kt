package com.memebattle.myapplication.core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.memebattle.myapplication.core.domain.model.NodeEntity
import io.reactivex.Single
import androidx.room.Update


@Dao
interface NodeDao {

    @Insert
    fun addNode(node: NodeEntity): Long

    @Query("SELECT * FROM nodeentity WHERE id = :id")
    fun getNode(id: Long): Single<NodeEntity>

    @Query("SELECT * FROM nodeentity")
    fun getAll(): Single<List<NodeEntity>>

    @Update
    fun update(node: NodeEntity)
}