package com.memebattle.myapplication.feature.node.child

import androidx.lifecycle.ViewModel
import com.memebattle.myapplication.App
import com.memebattle.myapplication.core.domain.callbacks.EmptyCallback
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.core.domain.interactor.NodeService
import com.memebattle.myapplication.core.domain.model.NodeEntity
import javax.inject.Inject

class ChildViewModel : ViewModel() {

    @Inject
    lateinit var nodeService: NodeService

    init {
        App.component.inject(this)
    }

    fun getAllEntityNodes(callback: SuccessCallback<ArrayList<NodeEntity>>, id: Long) {
        nodeService.getAllNodesEntity(object : SuccessCallback<ArrayList<NodeEntity>> {
            override fun onError(t: Throwable) {
                callback.onError(t)
            }

            override fun onSuccess(result: ArrayList<NodeEntity>) {
                var currentNodeEntity = NodeEntity(0)
                result.forEach {
                    if (it.id == id) {
                        currentNodeEntity = it
                    }
                }
                result.remove(currentNodeEntity)
                callback.onSuccess(result)

            }
        })
    }

    fun deleteTie(id: Long, parentId: Long, callback: EmptyCallback) {
        nodeService.deleteTie(id, parentId, object : EmptyCallback {
            override fun onSuccess() {
                callback.onSuccess()
            }

            override fun onFailed(error: Throwable) {
                callback.onFailed(error)
            }
        })
    }

    fun addChild(id: Long, childId: Long, callback: EmptyCallback) {
        nodeService.addChild(id, childId, object : EmptyCallback {
            override fun onFailed(error: Throwable) {
                callback.onFailed(error)
            }

            override fun onSuccess() {
                callback.onSuccess()
            }

        })
    }
}