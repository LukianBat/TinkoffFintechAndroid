package com.memebattle.myapplication.core.domain.callbacks

interface EmptyCallback {
    fun onSuccess()
    fun onFailed(error: Throwable)
}