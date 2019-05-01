package com.memebattle.zaebumbainvest.feature.main.buy.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.Stock
import com.memebattle.zaebumbainvest.feature.main.buy.presentation.recycler.BuyStocksRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_buy_stocks.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.memebattle.zaebumbainvest.core.domain.*
import com.memebattle.zaebumbainvest.core.presentation.PaginationScrollListener
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bottom_dialog.view.*
import java.util.*
import java.util.concurrent.TimeUnit


class BuyStocksActivity : AppCompatActivity() {

    private lateinit var viewModel: BuyStocksViewModel
    private lateinit var adapter: BuyStocksRecyclerViewAdapter
    private var isRecyclerInit = false
    private var money = 0.0
    private var firstPage: Long = 1
    private var recyclerPage: Long = 1
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_stocks)
        money = intent.extras?.getDouble("Money") ?: 0.0
        App.instance.daggerComponentHelper.plusBuyStocksComponent()
        isRecyclerInit = false
        initRecycler(arrayListOf())
        initViewModel()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.daggerComponentHelper.removeBuyStocksComponent()

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(BuyStocksViewModel::class.java)
        viewModel.buyResLiveData.observe(this, Observer {
            Snackbar.make(buyStocksRecycler.rootView, transStatus(it), Snackbar.LENGTH_LONG).show()
        })
        viewModel.stocksLiveData.observe(this, Observer { info ->
            isLoading = false
            buyStocksSwipeContainer.isRefreshing = false
            buyProgressBar.visibility = View.GONE
            if (info.nextItemId == recyclerPage) {
                isLastPage = true
            }
            recyclerPage = info.nextItemId ?: 1
            buyStocksSwipeContainer.isRefreshing = false
            val items = info.items ?: return@Observer
            if (items.isEmpty().not()) {
                if (isRecyclerInit) {
                    if (info.nextItemId == firstPage && buyStocksRecycler.size <= 10) {
                        adapter.clear()
                    }
                    adapter.addAll(items)
                } else {
                    initRecycler(items)
                }
            }
            firstPage = info.prevItemId ?: 1

        })
        viewModel.errorLiveData.observe(this, Observer { error ->
            buyProgressBar.visibility = View.GONE
            snack(error)
            buyStocksSwipeContainer.isRefreshing = false
            isLoading = false
        })
    }

    private fun initRecycler(stocks: ArrayList<Stock>) {

        adapter = BuyStocksRecyclerViewAdapter(stocks, this, object : OnItemClickCallback {
            override fun onClick(position: Int) {
                openDialog(stocks[position])
            }
        })
        val linearLayoutManager = LinearLayoutManager(this)
        val itemAnimator = DefaultItemAnimator()
        val dividerItemDecoration = DividerItemDecoration(buyStocksRecycler.context, linearLayoutManager.orientation)
        buyStocksRecycler.addItemDecoration(dividerItemDecoration)
        buyStocksRecycler.adapter = adapter
        buyStocksRecycler.layoutManager = linearLayoutManager
        buyStocksRecycler.itemAnimator = itemAnimator
        buyStocksRecycler.addOnScrollListener(object : PaginationScrollListener(buyStocksRecycler.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                viewModel.getStocks(recyclerPage)
                isLoading = true

            }
        })
        isRecyclerInit = true
    }

    @SuppressLint("InflateParams")
    private fun openDialog(stock: Stock) {
        val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        view.transButton.setText(R.string.buy_text)
        view.stockTransName.text = stock.name
        view.transButton.setOnClickListener {
            if (view.stockCountTrans.isCorrectCurrent() && money >= view.stockCountTrans.text.toString().toLong() * stock.price && view.stockCountTrans.text.toString().toLong() > 0) {
                viewModel.buyStocks(stock.id, view.stockCountTrans.text.toString().toLong())
                money -= view.stockCountTrans.text.toString().toLong() * stock.price
                dialog.dismiss()
            } else {
                toast(getString(R.string.input_error))
            }
        }
        dialog.show()
    }

    private fun initView() {
        buyStocksSwipeContainer.setOnRefreshListener {
            if (checkInternet()) {
                adapter.clear()
                recyclerPage = 1
                firstPage = 1
                isLastPage = false
                isLoading = false
                viewModel.getStocks(recyclerPage)
            } else {
                snack(getString(R.string.connect_error))
                buyStocksSwipeContainer.isRefreshing = false
            }
        }
        viewModel.getStocks(recyclerPage)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchViewItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchViewItem.actionView as SearchView
        viewModel.compositeDisposable.add(Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchView.clearFocus()
                    subscriber.onNext(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    subscriber.onNext(newText)
                    return false
                }
            })
        }).filter(Predicate { str ->
            adapter.filter.filter(str)
            return@Predicate str.length > 2
        })
                .debounce(350, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { text ->
                    adapter.clear()
                    buyProgressBar.visibility = View.VISIBLE
                    viewModel.searchByName(text)
                })
        return super.onCreateOptionsMenu(menu)
    }
}
