package com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain

import com.memebattle.zaebumbainvest.feature.main.fragments.profile.data.ApiProfile
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.AccountInfo
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.SellReq
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.SellRes
import io.reactivex.Single

class ProfileApiServiceImpl(var api: ApiProfile) : ProfileApiService {

    override fun getProfile(accessToken: String): Single<AccountInfo> =
            api.getProfile(accessToken)


    override fun sellStock(accessToken: String, stockId: Long, amount: Long): Single<SellRes> =
            api.sellStock(accessToken, SellReq(stockId, amount))

}