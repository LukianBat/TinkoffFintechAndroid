package com.memebattle.myapplication.feature.node.parents

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.NonNull
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

class ParentsFragment : Fragment() {

    private lateinit var viewModel: ParentsViewModel
    var id: Long = 0
    override fun onCreateView(@NonNull inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ParentsViewModel::class.java)
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
                initRecycler(result.toList())
            }
        }, this.id)
    }

    private fun initRecycler(nodes: List<NodeEntity>) {
        val adapter = TiesRecyclerViewAdapter(nodes, context!!, object : OnTieClickCallback {
            override fun onAddClick(id: Long) {
                viewModel.addParent(this@ParentsFragment.id, id, object : EmptyCallback {
                    override fun onSuccess() {
                        getAllNodes()
                    }

                    override fun onFailed(error: Throwable) {

                    }
                })
            }

            override fun onDeleteClick(id: Long) {
                viewModel.deleteTie(this@ParentsFragment.id, id, object : EmptyCallback {
                    override fun onSuccess() {
                        getAllNodes()
                    }

                    override fun onFailed(error: Throwable) {

                    }
                })
            }

        }, true, this.id)
        val linearLayoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        tiesRecyclerView.adapter = adapter
        tiesRecyclerView.layoutManager = linearLayoutManager
        tiesRecyclerView.itemAnimator = itemAnimator
    }
}