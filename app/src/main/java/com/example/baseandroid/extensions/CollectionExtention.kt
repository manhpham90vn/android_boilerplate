package com.example.baseandroid.extensions

fun <T> List<T>.getCountItemInList(count: Int): MutableList<T> {
    val newList = mutableListOf<T>()
    when {
        this.isEmpty() -> {
        }
        this.size < count -> {
            newList.addAll(this)
        }
        else -> {
            newList.addAll(this.subList(0, count))
        }
    }
    return newList
}

fun <T> List<T>?.checkCanLoadMore(perPage: Int): Boolean {
    return if (!this.isNullOrEmpty()) {
        this.size <= perPage
    } else {
        false
    }
}

