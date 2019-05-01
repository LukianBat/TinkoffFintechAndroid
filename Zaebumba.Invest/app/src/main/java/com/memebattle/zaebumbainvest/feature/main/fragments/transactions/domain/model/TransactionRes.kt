package com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model

data class TransactionRes(val nextItemId: Long?, val prevItemId: Long?, val items: ArrayList<Transaction>?)

data class Transaction(val transactionId: Long, val stock: Stock?, val amount: Long, val totalPrice: Double, val date: String, val type: String)

data class Stock(val id: Long, val code: String, val name: String, val iconUrl: String)
