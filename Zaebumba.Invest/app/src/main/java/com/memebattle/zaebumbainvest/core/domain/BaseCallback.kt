package com.memebattle.zaebumbainvest.core.domain

interface BaseCallback<T> {
    fun onSuccess(result: T)
}