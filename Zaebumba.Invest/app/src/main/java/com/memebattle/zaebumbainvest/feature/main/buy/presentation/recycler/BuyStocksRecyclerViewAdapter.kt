package com.memebattle.zaebumbainvest.feature.main.buy.presentation.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.feature.main.buy.domain.model.Stock
import com.memebattle.zaebumbainvest.core.domain.OnItemClickCallback
import kotlinx.android.synthetic.main.profile_stock_item.view.*
import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class BuyStocksRecyclerViewAdapter(private val stocks: ArrayList<Stock>, private val context: Context, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<BuyStocksRecyclerViewAdapter.StocksViewHolder>(), Filterable {
    private var filteredStocks: ArrayList<Stock>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        return StocksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.buy_stock_item, parent, false))
    }

    init {
        filteredStocks = stocks
    }

    override fun getItemCount(): Int {
        return filteredStocks.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            -1
        } else position
    }

    fun clear() {
        stocks.clear()
        filteredStocks.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<Stock>) {
        stocks.addAll(list)
        filteredStocks = stocks
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        val itemView = holder.itemView
        itemView.setOnClickListener {
            onItemClickCallback.onClick(position)
        }
        Glide.with(itemView)
                .load(stocks[position].iconUrl)
                .apply(RequestOptions()
                        .placeholder(R.drawable.stocks_shape).fitCenter())
                .into(itemView.stockImage)
        itemView.stockName.text = filteredStocks[position].name
        itemView.price.text = "${filteredStocks[position].price} руб"
        if (filteredStocks[position].priceDelta > 0) {
            itemView.priceDelta.text = """↑${filteredStocks[position].priceDelta} руб"""
            itemView.priceDelta.setTextColor(Color.parseColor(context.getString(R.string.greenTextColor)))
        } else if (filteredStocks[position].priceDelta < 0) {
            itemView.priceDelta.text = """↓${Math.abs(filteredStocks[position].priceDelta)} руб"""
            itemView.priceDelta.setTextColor(Color.parseColor(context.getString(R.string.redTextColor)))

        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredStocks = if (charString.isEmpty()) {
                    stocks
                } else {
                    val filteredList = ArrayList<Stock>()
                    for (row in stocks) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredStocks
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredStocks = filterResults.values as ArrayList<Stock>
                notifyDataSetChanged()
            }
        }
    }

    class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
