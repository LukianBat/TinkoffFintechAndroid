package com.memebattle.myapplication.core.domain.model

data class Node(val id: Long, val value: Int, val nodeList: List<Node>)