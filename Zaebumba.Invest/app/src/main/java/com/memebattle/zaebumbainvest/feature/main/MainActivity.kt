package com.memebattle.zaebumbainvest.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.daggerComponentHelper.plusMainComponent()
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.daggerComponentHelper.removeMainComponent()
    }
}
