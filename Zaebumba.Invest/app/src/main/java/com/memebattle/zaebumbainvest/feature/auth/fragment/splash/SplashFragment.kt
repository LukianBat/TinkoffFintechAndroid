package com.memebattle.zaebumbainvest.feature.auth.fragment.splash


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.feature.main.MainActivity
import java.util.*
import android.annotation.SuppressLint
import android.os.Handler
import androidx.navigation.Navigation


class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var handler: Handler
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initViewModel()
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: android.os.Message) {
                viewModel.authDataCheck()
            }
        }
        navigationNext()
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.authCheckData.observe(this, androidx.lifecycle.Observer {
            if (it) {
                startActivity(Intent(activity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            } else {
                val navController = Navigation.findNavController(activity!!, R.id.nav_host_auth)
                navController.navigate(R.id.action_splashFragment_to_signInFragment)
            }
        })
    }

    private fun navigationNext() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.sendEmptyMessage(1)

            }
        }, 1000)
    }
}
