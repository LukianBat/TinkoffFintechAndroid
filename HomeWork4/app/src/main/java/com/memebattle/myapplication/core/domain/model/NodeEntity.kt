package com.memebattle.myapplication.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.memebattle.myapplication.core.domain.NodeListConverter
import androidx.room.TypeConverters


@Entity
class NodeEntity(var value: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @TypeConverters(NodeListConverter::class)
    var child = ArrayList<Node>()

    @TypeConverters(NodeListConverter::class)
    var parents = ArrayList<Node>()

    fun nodeEntityToNode(): Node {
        val listNode = child
        listNode.addAll(parents)
        return Node(id, value, listNode.toList())
    }

}