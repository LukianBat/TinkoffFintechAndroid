package com.memebattle.myapplication.feature.list.recycler

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.memebattle.myapplication.R
import com.memebattle.myapplication.core.domain.model.NodeEntity
import com.memebattle.myapplication.core.domain.callbacks.OnItemClickCallback
import kotlinx.android.synthetic.main.node_list_item.view.*


class NodesRecyclerViewAdapter(private val nodes: List<NodeEntity>, private val context: Context, private val callback: OnItemClickCallback) : RecyclerView.Adapter<NodesRecyclerViewAdapter.NodesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.node_list_item, parent, false)
        return NodesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return nodes.size
    }

    override fun onBindViewHolder(holder: NodesViewHolder, position: Int) {
        val itemView = holder.itemView
        if (nodes[position].child.size > 0 && nodes[position].parents.size > 0) {
            itemView.setBackgroundColor(Color.parseColor(context.resources.getString(R.string.colorRed)))
        } else if (nodes[position].child.size > 0) {
            itemView.setBackgroundColor(Color.parseColor(context.resources.getString(R.string.colorYellow)))
        } else if (nodes[position].parents.size > 0) {
            itemView.setBackgroundColor(Color.parseColor(context.resources.getString(R.string.colorBlue)))
        }
        itemView.textViewIdItem.text = nodes[position].id.toString()
        itemView.textViewValueItem.text = nodes[position].value.toString()
        itemView.setOnClickListener {
            callback.onItemClick(nodes[position].id)
        }
    }

    class NodesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}