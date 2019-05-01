package com.memebattle.myapplication.feature.add

import android.util.Log
import androidx.lifecycle.ViewModel
import com.memebattle.myapplication.App
import com.memebattle.myapplication.core.domain.callbacks.EmptyCallback
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.core.domain.interactor.NodeService
import com.memebattle.myapplication.core.domain.model.Node
import com.memebattle.myapplication.core.domain.model.NodeEntity
import javax.inject.Inject


class AddViewModule : ViewModel() {

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

    fun addNodeEntity(value: Int, idParent: Long, idChild: Long, callback: EmptyCallback) {

        val parents = arrayListOf<Node>()
        val child = arrayListOf<Node>()
        nodeService.getNode(idParent, object : SuccessCallback<Node> {
            override fun onError(t: Throwable) {
                nodeService.getNode(idChild, object : SuccessCallback<Node> {
                    override fun onError(t: Throwable) {
                        nodeService.addNodeEntity(value, parents, child, object : EmptyCallback {
                            override fun onSuccess() {
                                callback.onSuccess()
                            }

                            override fun onFailed(error: Throwable) {
                                callback.onFailed(error)
                            }
                        })
                    }

                    override fun onSuccess(result: Node) {
                        child.add(result)
                        nodeService.addNodeEntity(value, parents, child, object : EmptyCallback {
                            override fun onSuccess() {
                                callback.onSuccess()
                            }

                            override fun onFailed(error: Throwable) {
                                callback.onFailed(error)
                            }
                        })
                    }
                })
            }

            override fun onSuccess(result: Node) {
                parents.add(result)
                nodeService.getNode(idChild, object : SuccessCallback<Node> {
                    override fun onError(t: Throwable) {
                        nodeService.addNodeEntity(value, parents, child, object : EmptyCallback {
                            override fun onSuccess() {
                                callback.onSuccess()
                            }

                            override fun onFailed(error: Throwable) {
                                callback.onFailed(error)
                            }
                        })
                    }

                    override fun onSuccess(result: Node) {
                        child.add(result)
                        nodeService.addNodeEntity(value, parents, child, object : EmptyCallback {
                            override fun onSuccess() {
                                callback.onSuccess()
                            }

                            override fun onFailed(error: Throwable) {
                                callback.onFailed(error)
                            }
                        })
                    }
                })
            }
        })
    }
}