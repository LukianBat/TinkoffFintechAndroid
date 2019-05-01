package com.memebattle.myapplication.feature.add


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.memebattle.myapplication.R
import com.memebattle.myapplication.core.domain.callbacks.EmptyCallback
import kotlinx.android.synthetic.main.fragment_add_node.*
import com.google.android.material.snackbar.Snackbar
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.core.domain.model.NodeEntity
import com.memebattle.myapplication.feature.list.NodesListFragment


class AddNodeFragment : Fragment() {

    private lateinit var viewModel: AddViewModule
    private lateinit var noTies: String
    private lateinit var manager: FragmentManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(AddViewModule::class.java)
        manager = activity!!.supportFragmentManager
        noTies = resources.getString(R.string.no_ties)
        return inflater.inflate(R.layout.fragment_add_node, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initSpinner()
        addNodeButton.setOnClickListener {
            var parentId = 0.toLong()
            var childId = 0.toLong()
            var value = 0
            if (spinnerParents.selectedItem.toString() != noTies) {
                parentId = spinnerParents.selectedItem.toString().toLong()
            }
            if (spinnerChild.selectedItem.toString() != noTies) {
                childId = spinnerChild.selectedItem.toString().toLong()
            }
            if (valueEdtText.text!!.isNotEmpty()) {
                value = valueEdtText.text.toString().toInt()
            }
            addNode(value, parentId, childId)
        }
    }

    private fun addNode(value: Int, parentId: Long, childId: Long) {
        viewModel.addNodeEntity(value, parentId, childId, object : EmptyCallback {
            override fun onSuccess() {
                val transaction = manager.beginTransaction()
                view?.let { Snackbar.make(it, resources.getString(R.string.success_added), Snackbar.LENGTH_LONG).show() }
                transaction
                        .remove(this@AddNodeFragment)
                        .add(R.id.container, NodesListFragment())
                        .commit()
            }

            override fun onFailed(error: Throwable) {
                view?.let { Snackbar.make(it, error.message.toString(), Snackbar.LENGTH_LONG).show() }
            }
        })
    }

    private fun initSpinner() {
        val fields = ArrayList<String>()
        fields.add(noTies)
        val adapter = ArrayAdapter<String>(context!!, R.layout.spinner_item, fields)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        viewModel.getAllEntityNodes(object : SuccessCallback<ArrayList<NodeEntity>> {
            override fun onSuccess(result: ArrayList<NodeEntity>) {
                result.forEach {
                    fields.add(it.id.toString())
                }
                spinnerParents.adapter = adapter
                spinnerChild.adapter = adapter
            }

            override fun onError(t: Throwable) {

            }
        })
    }
}
