package com.memebattle.zaebumbainvest.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.core.domain.alertInfo
import com.memebattle.zaebumbainvest.core.domain.checkInternet


class AuthActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.daggerComponentHelper.plusAuthComponent()
        setContentView(R.layout.activity_auth)
        if (checkInternet().not()) {
            alertInfo(getString(R.string.connect_error))
        }
        navController = Navigation.findNavController(this, R.id.nav_host_auth)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.daggerComponentHelper.removeAuthComponent()
    }

    override fun onBackPressed() {
        if (navController.currentDestination!!.id == R.id.signInFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
