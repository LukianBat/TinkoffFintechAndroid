package com.memebattle.zaebumbainvest.core.di.sub.auth

import com.memebattle.zaebumbainvest.core.di.core.scope.ActivityScope
import com.memebattle.zaebumbainvest.core.di.sub.auth.module.AuthApiModule
//import com.memebattle.zaebumbainvest.core.di.sub.auth.module.AuthNavigationModule
import com.memebattle.zaebumbainvest.core.di.sub.auth.module.AuthSettingsModule
import com.memebattle.zaebumbainvest.feature.auth.fragment.signin.SignInViewModel
import com.memebattle.zaebumbainvest.feature.auth.fragment.signup.presentation.SignUpViewModel
import com.memebattle.zaebumbainvest.feature.auth.fragment.splash.SplashViewModel
import dagger.Subcomponent

@Subcomponent(modules = [AuthApiModule::class, AuthSettingsModule::class])
@ActivityScope
interface AuthComponent {
    fun inject(signInViewModel: SignInViewModel)
    fun inject(signUpViewModel: SignUpViewModel)
    fun inject(splashViewModel: SplashViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): AuthComponent
    }
}