package com.mr.base.net

interface RequestListener<E> {
    fun onStart()

    fun onSuccess(result: E?) {}

    fun onSuccess(result: E?, msg: String?) {}

    fun onError(e: Throwable?)

    fun onError(e: Throwable?, businessCode: String?) {}

    fun onComplete()
}