package com.memebattle.myapplication.core.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.memebattle.myapplication.core.domain.model.Node


class NodeListConverter {

    @TypeConverter
    fun toNodesList(str: String): ArrayList<Node> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Node>>() {}.type
        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun fromNodeList(nodes: ArrayList<Node>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Node>>() {}.type
        return gson.toJson(nodes, type)
    }
}