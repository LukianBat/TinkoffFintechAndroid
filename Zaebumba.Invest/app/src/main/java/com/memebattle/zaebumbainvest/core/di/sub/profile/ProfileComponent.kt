package com.memebattle.zaebumbainvest.core.di.sub.profile

import com.memebattle.zaebumbainvest.core.di.core.scope.FragmentScope
import com.memebattle.zaebumbainvest.core.di.sub.profile.module.ProfileApiModule
import com.memebattle.zaebumbainvest.feature.main.fragments.account.AccViewModel
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.presentation.ProfileViewModel
import dagger.Subcomponent

@Subcomponent(modules = [ProfileApiModule::class])
@FragmentScope
interface ProfileComponent {
    fun inject(profileViewModel: ProfileViewModel)
    fun inject(accViewModel: AccViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ProfileComponent
    }
}