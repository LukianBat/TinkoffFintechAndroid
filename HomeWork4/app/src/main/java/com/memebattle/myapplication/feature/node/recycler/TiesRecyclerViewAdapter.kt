package com.memebattle.myapplication.feature.node.recycler

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memebattle.myapplication.R
import com.memebattle.myapplication.core.domain.model.NodeEntity
import com.memebattle.myapplication.core.domain.callbacks.OnTieClickCallback
import kotlinx.android.synthetic.main.dependecy_list_item.view.*

class TiesRecyclerViewAdapter(private val nodes: List<NodeEntity>, private val context: Context, private val callback: OnTieClickCallback, private val isParent: Boolean, private val id: Long) : RecyclerView.Adapter<TiesRecyclerViewAdapter.TiesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dependecy_list_item, parent, false)
        return TiesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nodes.size
    }

    override fun onBindViewHolder(holder: TiesViewHolder, position: Int) {
        val itemView = holder.itemView
        if (isParent) {
            nodes[position].child.forEach {
                if (it.id == this.id) {
                    itemView.setBackgroundColor(Color.parseColor(context.resources.getString(R.string.colorAccent)))
                    itemView.deleteTieImage.visibility = View.VISIBLE
                    itemView.addTieImage.visibility = View.GONE
                }
            }
        }
        if (!isParent) {
            nodes[position].parents.forEach {
                if (it.id == this.id) {
                    itemView.setBackgroundColor(Color.parseColor(context.resources.getString(R.string.colorAccent)))
                    itemView.deleteTieImage.visibility = View.VISIBLE
                    itemView.addTieImage.visibility = View.GONE
                }
            }
        }
        itemView.textViewIdDependencyItem.text = nodes[position].id.toString()
        itemView.textViewValueDependencyItem.text = nodes[position].value.toString()
        itemView.deleteTieImage.setOnClickListener {
            callback.onDeleteClick(nodes[position].id)
        }
        itemView.addTieImage.setOnClickListener {
            callback.onAddClick(nodes[position].id)
        }
    }

    class TiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}