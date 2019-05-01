package com.memebattle.zaebumbainvest.feature.auth.fragment.signin


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.core.domain.isCorrectCurrent
import kotlinx.android.synthetic.main.auth_include.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import com.memebattle.zaebumbainvest.core.domain.snack
import com.memebattle.zaebumbainvest.feature.main.MainActivity


class SignInFragment : Fragment() {

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initViewModel()
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        viewModel.authLiveData.observe(this, Observer {
            startActivity(Intent(activity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        })
        viewModel.errorLiveData.observe(this, Observer { error ->
            snack(error)
            signInprogressBar.visibility = View.GONE
            signInButton.isClickable = true
        })
    }

    private fun initView() {
        signUpText.setOnClickListener {
            val activity = activity ?: return@setOnClickListener
            val navController = Navigation.findNavController(activity, R.id.nav_host_auth)
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        signInButton.setOnClickListener {
            signInprogressBar.visibility = View.VISIBLE
            signInButton.isClickable = false
            if (loginEditText.isCorrectCurrent() && passEditText.isCorrectCurrent()) {
                viewModel.signIn(loginEditText.text.toString(), passEditText.text.toString())
            } else {
                snack(getString(R.string.input_error))
                signInButton.isClickable = true
                signInprogressBar.visibility = View.GONE
            }
        }
    }
}
