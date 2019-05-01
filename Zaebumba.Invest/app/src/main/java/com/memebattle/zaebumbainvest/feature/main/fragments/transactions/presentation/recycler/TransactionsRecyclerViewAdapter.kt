package com.memebattle.zaebumbainvest.feature.main.fragments.transactions.presentation.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.memebattle.zaebumbainvest.R
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.Stock
import com.memebattle.zaebumbainvest.feature.main.fragments.transactions.domain.model.Transaction
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionsRecyclerViewAdapter(private val transactions: ArrayList<Transaction>, private val context: Context) : RecyclerView.Adapter<TransactionsRecyclerViewAdapter.StocksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_item, parent, false)
        return StocksViewHolder(view)

    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun clear() {
        transactions.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<Transaction>) {
        transactions.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        val itemView = holder.itemView
        val stock: Stock = transactions[position].stock ?: return

        itemView.stockName.text = stock.name
        itemView.price.text = "${transactions[position].totalPrice} руб"
        itemView.codeAndCountText.text = "${stock.code} • ${transactions[position].amount}шт"
        itemView.dateText.text = transactions[position].date.substring(0,transactions[position].date.indexOf("T"))
        Glide.with(itemView)
                .load(stock.iconUrl)
                .apply(RequestOptions()
                        .placeholder(R.drawable.stocks_shape).fitCenter())
                .into(itemView.stockImage)

        if (transactions[position].type == context.getString(R.string.type_buy)) {
            itemView.typeMarker.setImageResource(R.color.greenTextColor)
        } else if (transactions[position].type == context.getString(R.string.type_sell)) {
            itemView.typeMarker.setImageResource(R.color.redTextColor)

        }

    }

    class StocksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}