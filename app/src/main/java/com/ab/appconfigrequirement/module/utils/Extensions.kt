package com.ab.appconfigrequirement.module.utils

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(item)
    this.value = value
}

operator fun <T> MutableLiveData<MutableList<T>>.minusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.remove(item)
    this.value = value
}