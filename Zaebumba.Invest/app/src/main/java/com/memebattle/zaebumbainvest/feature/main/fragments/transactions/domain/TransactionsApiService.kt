package com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain

import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.data.ApiTransactions
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.Transaction
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.TransactionRes
import io.reactivex.Single

class TransactionsApiService(val api: ApiTransactions) {
    fun getTransactions(accessToken: String, lastId: Long): Single<TransactionRes> =
            api.getTransactions(accessToken, lastId)

}