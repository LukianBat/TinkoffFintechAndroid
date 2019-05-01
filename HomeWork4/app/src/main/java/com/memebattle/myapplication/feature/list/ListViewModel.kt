package com.memebattle.myapplication.feature.list

import androidx.lifecycle.ViewModel
import com.memebattle.myapplication.App
import com.memebattle.myapplication.core.domain.callbacks.EmptyCallback
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.core.domain.interactor.NodeService
import com.memebattle.myapplication.core.domain.model.Node
import com.memebattle.myapplication.core.domain.model.NodeEntity
import javax.inject.Inject

class ListViewModel : ViewModel() {
    @Inject
    lateinit var nodeService: NodeService

    init {
        App.component.inject(this)
    }

    fun getAllEntityNodes(callback: SuccessCallback<ArrayList<NodeEntity>>) {
        nodeService.getAllNodesEntity(object : SuccessCallback<ArrayList<NodeEntity>> {
            override fun onError(t: Throwable) {
                callback.onError(t)
            }

            override fun onSuccess(result: ArrayList<NodeEntity>) {
                callback.onSuccess(result)
            }
        })
    }

}