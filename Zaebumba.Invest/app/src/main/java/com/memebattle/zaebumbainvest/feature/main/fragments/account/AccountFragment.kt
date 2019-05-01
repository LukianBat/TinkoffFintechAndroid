package com.memebattle.zaebumbainvest.feature.main.fragments.account


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.core.domain.snack
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment() {


    private lateinit var viewModel: AccViewModel

    init {
        App.instance.daggerComponentHelper.mainComponent!!.inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AccViewModel::class.java)
        viewModel.errorLiveData.observe(this, Observer { error ->
            snack(error)
        })
        viewModel.profileLiveData.observe(this, Observer { info ->
            moneyAcc.text = """${info.balance} ₽"""
            nameAcc.text = info.name
        })
    }

    private fun initView() {
        viewModel.getProfile()
        val activity: Activity = activity ?: return
        val context: Context = context ?: return

        toolbarAcc.setNavigationIcon(R.drawable.ic_back_button)
        toolbarAcc.setNavigationOnClickListener { activity.onBackPressed() }
        signOut.setOnClickListener {
            viewModel.clearToken()
            context.cacheDir.deleteRecursively()
            activity.finish()
        }
    }
}
