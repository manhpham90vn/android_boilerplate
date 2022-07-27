package com.example.baseandroid.ui.view // ktlint-disable filename

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgURL")
fun loadImage(image: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(image.context).load(url).centerCrop().into(image)
    }
}
