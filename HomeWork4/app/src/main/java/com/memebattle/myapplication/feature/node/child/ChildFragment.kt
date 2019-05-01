package com.memebattle.myapplication.feature.node.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.memebattle.myapplication.R
import com.memebattle.myapplication.core.domain.callbacks.EmptyCallback
import com.memebattle.myapplication.core.domain.model.NodeEntity
import com.memebattle.myapplication.core.domain.callbacks.OnTieClickCallback
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.feature.node.recycler.TiesRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_dependency.*

class ChildFragment : Fragment() {
    private lateinit var viewModel: ChildViewModel
    var id: Long = 0
    override fun onCreateView(@NonNull inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ChildViewModel::class.java)
        return inflater.inflate(R.layout.fragment_dependency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllNodes()
    }

    private fun getAllNodes() {
        viewModel.getAllEntityNodes(object : SuccessCallback<ArrayList<NodeEntity>> {
            override fun onError(t: Throwable) {

            }

            override fun onSuccess(result: ArrayList<NodeEntity>) {
                initRecycler(result)
            }
        }, this.id)
    }

    private fun initRecycler(nodes: List<NodeEntity>) {
        val adapter = TiesRecyclerViewAdapter(nodes, context!!, object : OnTieClickCallback {
            override fun onAddClick(id: Long) {
                viewModel.addChild(this@ChildFragment.id, id, object : EmptyCallback {
                    override fun onSuccess() {
                        getAllNodes()
                    }

                    override fun onFailed(error: Throwable) {

                    }
                })
            }

            override fun onDeleteClick(id: Long) {
                viewModel.deleteTie(this@ChildFragment.id, id, object : EmptyCallback {
                    override fun onSuccess() {
                        getAllNodes()
                    }

                    override fun onFailed(error: Throwable) {

                    }
                })
            }

        }, false, this.id)
        val linearLayoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        tiesRecyclerView.adapter = adapter
        tiesRecyclerView.layoutManager = linearLayoutManager
        tiesRecyclerView.itemAnimator = itemAnimator
    }
}