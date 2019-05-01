package com.memebattle.myapplication.core.domain.interactor

import com.memebattle.myapplication.core.domain.callbacks.EmptyCallback
import com.memebattle.myapplication.core.domain.callbacks.SuccessCallback
import com.memebattle.myapplication.core.data.NodeDao
import com.memebattle.myapplication.core.domain.model.Node
import com.memebattle.myapplication.core.domain.model.NodeEntity
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver


class NodeService(private val dao: NodeDao) {

    fun addNodeEntity(value: Int, parents: ArrayList<Node>, child: ArrayList<Node>, callback: EmptyCallback) {
        val node = NodeEntity(value)
        node.child = child
        node.parents = parents
        Single.create(SingleOnSubscribe<Long> {
            it.onSuccess(dao.addNode(node))
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(object : SingleObserver<Long> {
                    override fun onSuccess(t: Long) {
                        if (child.size != 0) {
                            addParent(child.toList()[0].id, t, object : EmptyCallback {
                                override fun onSuccess() {
                                    if (parents.size != 0) {
                                        addChild(parents.toList()[0].id, t, object : EmptyCallback {
                                            override fun onSuccess() {
                                                callback.onSuccess()
                                            }

                                            override fun onFailed(error: Throwable) {
                                                callback.onSuccess()
                                            }
                                        })
                                    } else {
                                        callback.onSuccess()
                                    }
                                }

                                override fun onFailed(error: Throwable) {
                                    if (parents.size != 0) {
                                        addChild(parents.toList()[0].id, t, object : EmptyCallback {
                                            override fun onSuccess() {
                                                callback.onSuccess()
                                            }

                                            override fun onFailed(error: Throwable) {
                                                callback.onSuccess()
                                            }
                                        })
                                    } else {
                                        callback.onSuccess()
                                    }
                                }
                            })
                        } else {
                            callback.onSuccess()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        callback.onFailed(e)
                    }
                })
    }

    fun getNodeEntity(id: Long, callback: SuccessCallback<NodeEntity>) {
        dao.getNode(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSingleObserver<NodeEntity>() {
                    override fun onError(e: Throwable) {
                        callback.onError(e)
                    }

                    override fun onSuccess(nodeEntity: NodeEntity) {
                        callback.onSuccess(nodeEntity)
                    }
                })
    }

    fun getNode(id: Long, callback: SuccessCallback<Node>) {
        dao.getNode(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSingleObserver<NodeEntity>() {
                    override fun onError(e: Throwable) {
                        callback.onError(e)
                    }

                    override fun onSuccess(nodeEntity: NodeEntity) {
                        callback.onSuccess(nodeEntity.nodeEntityToNode())
                    }
                })
    }

    fun getAllNodesEntity(callback: SuccessCallback<ArrayList<NodeEntity>>) {
        dao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableSingleObserver<List<NodeEntity>>() {
                    override fun onError(e: Throwable) {
                        callback.onError(e)
                    }

                    override fun onSuccess(t: List<NodeEntity>) {
                        val list = arrayListOf<NodeEntity>()
                        list.addAll(t)
                        callback.onSuccess(list)
                    }
                })
    }

    fun deleteTie(firstId: Long, secondId: Long, callback: EmptyCallback) {
        getNodeEntity(firstId, object : SuccessCallback<NodeEntity> {
            override fun onError(t: Throwable) {
                callback.onFailed(t)
            }

            override fun onSuccess(firstEntity: NodeEntity) {
                getNodeEntity(secondId, object : SuccessCallback<NodeEntity> {
                    override fun onError(t: Throwable) {
                        callback.onFailed(t)
                    }

                    override fun onSuccess(secondEntity: NodeEntity) {

                        Single.create(SingleOnSubscribe<Any> { it ->

                            val newFirstEntity = NodeEntity(firstEntity.value)
                            newFirstEntity.id = firstId
                            newFirstEntity.parents.addAll(newFirstEntity.parents)
                            newFirstEntity.parents.forEach {
                                if (it.id == secondId) {
                                    newFirstEntity.parents.remove(it)
                                }
                            }
                            newFirstEntity.child.addAll(newFirstEntity.child)
                            newFirstEntity.child.forEach {
                                if (it.id == secondId) {
                                    newFirstEntity.child.remove(it)
                                }
                            }
                            dao.update(newFirstEntity)

                            val newSecondEntity = NodeEntity(secondEntity.value)
                            newSecondEntity.id = secondId
                            newSecondEntity.parents.addAll(secondEntity.parents)
                            newSecondEntity.parents.forEach {
                                if (it.id == firstId) {
                                    newSecondEntity.parents.remove(it)
                                }
                            }
                            newSecondEntity.child.addAll(newSecondEntity.child)
                            newSecondEntity.child.forEach {
                                if (it.id == firstId) {
                                    newSecondEntity.child.remove(it)
                                }
                            }
                            dao.update(newSecondEntity)


                            it.onSuccess(Any())
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers
                                        .mainThread())
                                .subscribe(object : SingleObserver<Any> {
                                    override fun onSuccess(t: Any) {
                                        callback.onSuccess()
                                    }

                                    override fun onSubscribe(d: Disposable) {

                                    }

                                    override fun onError(e: Throwable) {
                                        callback.onFailed(e)
                                    }
                                })
                    }
                })
            }
        })
    }

    fun addParent(id: Long, parentId: Long, callback: EmptyCallback) {
        getNodeEntity(id, object : SuccessCallback<NodeEntity> {
            override fun onError(t: Throwable) {
                callback.onFailed(t)
            }

            override fun onSuccess(childNodeEntity: NodeEntity) {
                getNodeEntity(parentId, object : SuccessCallback<NodeEntity> {
                    override fun onSuccess(parentNodeEntity: NodeEntity) {
                        Single.create(SingleOnSubscribe<Any> {

                            val newChildNodeEntity = NodeEntity(childNodeEntity.value)
                            newChildNodeEntity.id = id
                            newChildNodeEntity.parents.addAll(childNodeEntity.parents)
                            newChildNodeEntity.parents.add(parentNodeEntity.nodeEntityToNode())
                            newChildNodeEntity.child.addAll(childNodeEntity.child)
                            dao.update(newChildNodeEntity)

                            val newParentNodeEntity = NodeEntity(parentNodeEntity.value)
                            newParentNodeEntity.id = parentId
                            newParentNodeEntity.child.addAll(parentNodeEntity.parents)
                            newParentNodeEntity.child.add(newChildNodeEntity.nodeEntityToNode())
                            newParentNodeEntity.parents.addAll(parentNodeEntity.parents)
                            dao.update(newParentNodeEntity)

                            it.onSuccess(Any())
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers
                                        .mainThread())
                                .subscribe(object : SingleObserver<Any> {
                                    override fun onSuccess(t: Any) {
                                        callback.onSuccess()
                                    }

                                    override fun onSubscribe(d: Disposable) {

                                    }

                                    override fun onError(e: Throwable) {
                                        callback.onFailed(e)
                                    }
                                })
                    }

                    override fun onError(t: Throwable) {
                        callback.onFailed(t)
                    }
                })
            }
        })
    }

    fun addChild(id: Long, childId: Long, callback: EmptyCallback) {
        getNodeEntity(id, object : SuccessCallback<NodeEntity> {
            override fun onError(t: Throwable) {
                callback.onFailed(t)
            }

            override fun onSuccess(parentNodeEntity: NodeEntity) {
                getNodeEntity(childId, object : SuccessCallback<NodeEntity> {
                    override fun onError(t: Throwable) {
                        callback.onFailed(t)
                    }

                    override fun onSuccess(childNodeEntity: NodeEntity) {
                        Single.create(SingleOnSubscribe<Any> {

                            val newChildNodeEntity = NodeEntity(childNodeEntity.value)
                            newChildNodeEntity.id = childId
                            newChildNodeEntity.parents.addAll(childNodeEntity.parents)
                            newChildNodeEntity.parents.add(parentNodeEntity.nodeEntityToNode())
                            newChildNodeEntity.child.addAll(childNodeEntity.child)
                            dao.update(newChildNodeEntity)

                            val newParentNodeEntity = NodeEntity(parentNodeEntity.value)
                            newParentNodeEntity.id = id
                            newParentNodeEntity.child.addAll(parentNodeEntity.child)
                            newParentNodeEntity.child.add(newChildNodeEntity.nodeEntityToNode())
                            newParentNodeEntity.parents.addAll(parentNodeEntity.parents)
                            dao.update(newParentNodeEntity)

                            it.onSuccess(Any())
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers
                                        .mainThread())
                                .subscribe(object : SingleObserver<Any> {
                                    override fun onSuccess(t: Any) {
                                        callback.onSuccess()
                                    }

                                    override fun onSubscribe(d: Disposable) {

                                    }

                                    override fun onError(e: Throwable) {

                                    }
                                })

                    }
                })
            }
        })
    }
}