package com.memebattle.zaebumbainvest.feature.main.fragments.profile.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.presentation.recycler.ProfileRecyclerViewAdapter
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.Stock
import kotlinx.android.synthetic.main.fragment_profile.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.memebattle.zaebumbainvest.core.domain.*
import com.memebattle.zaebumbainvest.feature.main.buy.presentation.BuyStocksActivity
import kotlinx.android.synthetic.main.bottom_dialog.view.*
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private var nameTitle = ""
    private var moneyTitle = "0 "
    private var isRecyclerInit = false
    private lateinit var adapter: ProfileRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.daggerComponentHelper.plusProfileComponent()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.daggerComponentHelper.removeProfileComponent()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isRecyclerInit = false
        initViewModel()
        initView()
    }

    private fun openDialog(stock: Stock) {
        val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
        val context: Context = context ?: return
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(view)
        view.transButton.text = getString(R.string.sell_text)
        view.stockTransName.text = stock.name
        view.transButton.setOnClickListener {
            if (view.stockCountTrans.isCorrectCurrent() && view.stockCountTrans.text.toString().toLong() > 0 && view.stockCountTrans.text.toString().toLong() <= stock.count) {
                viewModel.sellStock(stock.id, view.stockCountTrans.text.toString().toLong())
                progressBar.visibility = View.VISIBLE
                money.visibility = View.GONE
                dialog.dismiss()
            } else {
                toast(getString(R.string.input_error))
            }
        }
        dialog.show()
    }

    private fun initViewModel() {
        val view: View = view ?: return
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.sellResLiveData.observe(this, Observer {
            viewModel.refreshProfile()
            Snackbar.make(profileRecycler.rootView, transStatus(it.status), Snackbar.LENGTH_LONG).show()
        })
        viewModel.errorLiveData.observe(this, Observer { error ->
            snack(error)
            profileSwipeContainer.isRefreshing = false

        })
        viewModel.profileLiveData.observe(this, Observer { info ->
            progressBar.visibility = View.GONE
            money.visibility = View.VISIBLE
            if (isRecyclerInit) {
                adapter.clear()
                adapter.addAll(info.stocks!!)
            } else initRecycler(info.stocks!!)

            moneyTitle = "${info.balance} ₽"
            nameTitle = info.name
            profileSwipeContainer.isRefreshing = false
            view.refreshDrawableState()
            if (info.stocks.isEmpty()) {
                snack(getString(R.string.noContent))
            }
        })
    }

    private fun initRecycler(stocks: ArrayList<Stock>) {
        val context: Context = context ?: return
        adapter = ProfileRecyclerViewAdapter(stocks, context, object : OnItemClickCallback {
            override fun onClick(position: Int) {
                openDialog(stocks[position])
            }
        })
        val linearLayoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        val dividerItemDecoration = DividerItemDecoration(profileRecycler.context, linearLayoutManager.orientation)
        profileRecycler.addItemDecoration(dividerItemDecoration)
        profileRecycler.adapter = adapter
        profileRecycler.layoutManager = linearLayoutManager
        profileRecycler.itemAnimator = itemAnimator
        isRecyclerInit = true

    }

    private fun initView() {
        viewModel.getCashProfile()
        transactionsImage.setOnClickListener {
            val navController = Navigation.findNavController(activity!!, R.id.nav_host_main)
            navController.navigate(R.id.action_profileFragment_to_transactionsFragment)
        }
        accImage.setOnClickListener {
            val navController = Navigation.findNavController(activity!!, R.id.nav_host_main)
            navController.navigate(R.id.action_profileFragment_to_accountFragment)
        }
        profileSwipeContainer.setOnRefreshListener {
            if (checkInternet().not()) {
                snack(getString(R.string.connect_error))
                profileSwipeContainer.isRefreshing = false
            } else {
                progressBar.visibility = View.VISIBLE
                money.visibility = View.GONE
                viewModel.refreshProfile()
            }
        }
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                name.text = moneyTitle
            } else {
                name.text = nameTitle
                money.text = moneyTitle
            }
        })
        floatingActionAddNoteButton.setOnClickListener {
            startActivity(Intent(activity, BuyStocksActivity::class.java).putExtra("Money", (moneyTitle.subSequence(0, moneyTitle.length - 1)).toString().toDouble()))
        }
    }

}
