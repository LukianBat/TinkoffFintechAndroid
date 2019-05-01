package com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain

import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.AccountInfo
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.SellRes
import io.reactivex.Single

interface ProfileApiService {
    fun getProfile(accessToken: String): Single<AccountInfo>
    fun sellStock(accessToken: String, stockId: Long, amount: Long): Single<SellRes>
}