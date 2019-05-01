package com.memebattle.zaebumbainvest.feature.main.fragments.profile.presentation.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.memebattle.zaebumbainvest.feature.main.fragments.profile.domain.model.Stock
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.core.domain.OnItemClickCallback
import kotlinx.android.synthetic.main.profile_stock_item.view.*


class ProfileRecyclerViewAdapter(private val stocks: ArrayList<Stock>, private val context: Context, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.StocksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_stock_item, parent, false)
        return StocksViewHolder(view)

    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun clear() {
        stocks.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<Stock>) {
        stocks.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        val itemView = holder.itemView
        itemView.setOnClickListener {
            onItemClickCallback.onClick(position)
        }
        itemView.stockName.text = stocks[position].name
        itemView.price.text = """${stocks[position].price} руб"""
        itemView.count.text = """${stocks[position].count}шт"""
        Glide.with(itemView)
                .load(stocks[position].iconUrl)
                .apply(RequestOptions()
                        .placeholder(R.drawable.stocks_shape).fitCenter())
                .into(itemView.stockImage)
        if (stocks[position].priceDelta > 0) {
            itemView.priceDelta.text = """↑${stocks[position].priceDelta} руб"""
            itemView.priceDelta.setTextColor(Color.parseColor(context.getString(R.string.greenTextColor)))
        } else if (stocks[position].priceDelta < 0) {
            itemView.priceDelta.text = """↓${Math.abs(stocks[position].priceDelta)} руб"""
            itemView.priceDelta.setTextColor(Color.parseColor(context.getString(R.string.redTextColor)))

        }

    }

    class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
