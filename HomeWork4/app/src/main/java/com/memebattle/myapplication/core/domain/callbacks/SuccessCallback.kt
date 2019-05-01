package com.memebattle.myapplication.core.domain.callbacks

interface SuccessCallback<T> {
    fun onSuccess(result: T)
    fun onError(t: Throwable)
}