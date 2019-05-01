package com.memebattle.zaebumbainvest.core.di.sub.profile.module

import com.memebattle.zaebumbainvest.core.di.core.scope.FragmentScope
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.ProfileApiService
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.data.ApiProfile
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.ProfileApiServiceImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named


@Module
class ProfileApiModule {

    @FragmentScope
    @Provides
    @Named("cash")
    fun provideProfileCashApiService(@Named("cash") retrofit: Retrofit): ProfileApiService =
            ProfileApiServiceImpl(retrofit.create(ApiProfile::class.java))

    @FragmentScope
    @Provides
    @Named("refresh")
    fun provideProfileApiService(@Named("refresh") retrofit: Retrofit): ProfileApiService =
            ProfileApiServiceImpl(retrofit.create(ApiProfile::class.java))

}