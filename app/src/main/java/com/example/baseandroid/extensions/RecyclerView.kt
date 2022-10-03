package com.example.baseandroid.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setOnScrollEndVerticalListener(callBack: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (canScrollVertically(-1) && !canScrollVertically(1)) {
                callBack.invoke()
            }
        }
    })
}