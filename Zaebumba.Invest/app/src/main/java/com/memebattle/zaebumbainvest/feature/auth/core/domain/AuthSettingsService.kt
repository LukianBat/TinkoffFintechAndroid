package com.memebattle.zaebumbainvest.feature.auth.core.domain

import android.content.Context
import android.content.SharedPreferences
import com.memebattle.zaebumbainvest.R

class AuthSettingsService(private val sharedPreferences: SharedPreferences, context: Context) {

    fun setTokens(refreshToken: String, accessToken: String) {
        sharedPreferences.edit().apply {
            putString(KEY_ACCESS, accessToken)
            putString(KEY_REFRESH, refreshToken)
            apply()
        }
    }

    fun getAccessToken(): String =
            sharedPreferences.getString(KEY_ACCESS, ERROR)!!

    companion object {
        const val KEY_REFRESH = "refresh token"
        const val KEY_ACCESS = "access token"
        const val ERROR = "error"
    }
}