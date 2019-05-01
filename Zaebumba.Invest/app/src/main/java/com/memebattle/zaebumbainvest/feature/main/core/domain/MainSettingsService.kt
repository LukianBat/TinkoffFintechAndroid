package com.memebattle.zaebumbainvest.feature.main.core.domain

import android.content.Context
import android.content.SharedPreferences
import com.memebattle.zaebumbainvest.R

class MainSettingsService(private val sharedPreferences: SharedPreferences, private val context: Context) {
    private val KEY_REFRESH = context.getString(R.string.key_refresh)
    private val KEY_ACCESS = context.getString(R.string.key_access)
    private val ERROR = context.getString(R.string.token_error)

    fun setTokens(refreshToken: String, accessToken: String) {
        sharedPreferences.edit().apply {
            putString(KEY_ACCESS, accessToken)
            putString(KEY_REFRESH, refreshToken)
            apply()
        }
    }

    fun clearTokens() {
        sharedPreferences.edit().clear().apply()
    }

    fun getAccessToken(): String = sharedPreferences.getString(KEY_ACCESS, ERROR)!!


    fun getRefreshToken(): String = sharedPreferences.getString(KEY_REFRESH, ERROR)!!

}