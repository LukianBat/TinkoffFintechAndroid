package com.memebattle.zaebumbainvest.feature.main.fragments.transactions.presentation


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.memebattle.zaebumbainvest.App
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.core.domain.checkInternet
import com.memebattle.zaebumbainvest.core.domain.snack
import com.memebattle.zaebumbainvest.core.presentation.PaginationScrollListener
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.Transaction
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.presentation.recycler.TransactionsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_transactions.*
import java.util.*


class TransactionsFragment : Fragment() {

    private lateinit var adapter: TransactionsRecyclerViewAdapter
    private var isRecyclerInit: Boolean = false
    private var recyclerPage: Long = 1
    private var firstPage: Long = 1
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    private lateinit var viewModel: TransactionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.daggerComponentHelper.plusTransactionsComponent()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.daggerComponentHelper.removeTransactionsComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isRecyclerInit = false
        initRecycler(arrayListOf())
        initViewModule()
        initView()
    }

    private fun initViewModule() {
        viewModel = ViewModelProviders.of(this).get(TransactionsViewModel::class.java)
        viewModel.errorLiveData.observe(this, Observer { error ->
            transProgressBar.visibility = View.GONE
            snack(error)
            transSwipeContainer.isRefreshing = false
            isLoading = false
        })
        viewModel.transactionsLiveData.observe(this, Observer { result ->
            isLoading = false
            transSwipeContainer.isRefreshing = false
            if (result.nextItemId == recyclerPage) {
                isLastPage = true
            }
            recyclerPage = result.nextItemId ?: 1
            val items = result.items ?: return@Observer
            transProgressBar.visibility = View.GONE
            if (items.isEmpty().not()) {
                if (isRecyclerInit) {
                    if (result.nextItemId == firstPage && transRecycler.size <= 10) {
                        adapter.clear()
                    }
                    adapter.addAll(items)
                } else initRecycler(items)
            }
            firstPage = result.prevItemId ?: 1
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.clear()
        recyclerPage = 1
        firstPage = 1
        isLastPage = false
        isLoading = false
    }
    private fun initView() {
        val view: View = view ?: return
        transSwipeContainer.setOnRefreshListener {
            if (checkInternet()) {
                adapter.clear()
                recyclerPage = 1
                firstPage = 1
                isLastPage = false
                isLoading = false
                viewModel.getTransactions(recyclerPage)

            } else {
                snack(getString(R.string.connect_error))
                transSwipeContainer.isRefreshing = false
            }
        }
        val toolbar = view.findViewById(R.id.toolbarTransactions) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener { activity!!.onBackPressed() }
        viewModel.getTransactions(recyclerPage)
    }

    private fun initRecycler(transactions: ArrayList<Transaction>) {
        val context: Context = context ?: return
        adapter = TransactionsRecyclerViewAdapter(transactions, context)
        val linearLayoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        val dividerItemDecoration = DividerItemDecoration(transRecycler.context, linearLayoutManager.orientation)
        transRecycler.addItemDecoration(dividerItemDecoration)
        transRecycler.adapter = adapter
        transRecycler.layoutManager = linearLayoutManager
        transRecycler.itemAnimator = itemAnimator
        transRecycler.addOnScrollListener(object : PaginationScrollListener(transRecycler.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                viewModel.getTransactions(recyclerPage)
                isLoading = true
            }
        })
        isRecyclerInit = true

    }
}
