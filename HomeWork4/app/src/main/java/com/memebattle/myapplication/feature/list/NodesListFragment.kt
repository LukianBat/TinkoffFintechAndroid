package com.memebattle.myapplication.feature.list


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.memebattle.myapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.core.domain.model.NodeEntity
import com.memebattle.myapplication.feature.add.AddNodeFragment
import com.memebattle.myapplication.core.domain.callbacks.OnItemClickCallback
import com.memebattle.myapplication.feature.list.recycler.NodesRecyclerViewAdapter
import com.memebattle.myapplication.feature.node.NodeFragment
import kotlinx.android.synthetic.main.fragment_nodes_list.*


class NodesListFragment : Fragment() {

    private lateinit var manager: FragmentManager
    private lateinit var viewModel: ListViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        manager = activity!!.supportFragmentManager
        return inflater.inflate(R.layout.fragment_nodes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllEntityNodes(object : SuccessCallback<ArrayList<NodeEntity>> {
            override fun onError(t: Throwable) {

            }

            override fun onSuccess(result: ArrayList<NodeEntity>) {
                initRecycler(result)
            }
        })
        initView()
    }

    private fun initRecycler(nodes: List<NodeEntity>) {
        val adapter = NodesRecyclerViewAdapter(nodes, context!!, object : OnItemClickCallback {
            override fun onItemClick(id: Long) {
                val transaction = manager.beginTransaction()
                val nodeFragment = NodeFragment()
                nodeFragment.id = id
                transaction
                        .remove(this@NodesListFragment)
                        .add(R.id.container, nodeFragment)
                        .commit()
            }

        })
        val linearLayoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        nodeRecyclerView.adapter = adapter
        nodeRecyclerView.layoutManager = linearLayoutManager
        nodeRecyclerView.itemAnimator = itemAnimator
    }

    private fun initView() {
        floatingActionAddNoteButton.setOnClickListener {
            val fragment = AddNodeFragment()
            val transaction = manager.beginTransaction()
            transaction
                    .remove(this)
                    .add(R.id.container, fragment)
                    .commit()
        }
    }

}
