package com.example.baseandroid.extensions

fun <T> List<T>.getListItems(count: Int): MutableList<T> {
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

