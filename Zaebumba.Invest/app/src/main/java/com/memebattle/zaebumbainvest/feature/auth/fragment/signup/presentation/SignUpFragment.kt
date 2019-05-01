package com.memebattle.zaebumbainvest.feature.auth.fragment.signup.presentation


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.core.domain.isCorrectCurrent
import com.memebattle.zaebumbainvest.core.domain.snack
import com.memebattle.zaebumbainvest.feature.main.MainActivity
import kotlinx.android.synthetic.main.auth_include.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        viewModel.authLiveData.observe(this, Observer {
            startActivity(Intent(activity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        })
        viewModel.errorLiveData.observe(this, Observer { error ->
            snack(error)
            signUpProgressBar.visibility = View.GONE
            signUpButton.isClickable = true
        })
    }

    private fun initView() {
        signUpButton.setOnClickListener {
            signUpProgressBar.visibility = View.VISIBLE
            signUpButton.isClickable = false
            if (loginEditText.isCorrectCurrent() && passEditText.isCorrectCurrent()) {
                viewModel.signUp(loginEditText.text.toString(), passEditText.text.toString())
            } else {
                signUpButton.isClickable = true
                signUpProgressBar.visibility = View.GONE
                snack(getString(R.string.input_error))
            }
        }
    }
}
