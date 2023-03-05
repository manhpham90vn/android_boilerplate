package com.manhpham.baseandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manhpham.baseandroid.databinding.HomeItemBinding
import com.manhpham.baseandroid.models.PagingUserResponse

typealias HomeItemListener = (PagingUserResponse) -> Unit

class HomeAdapter : PagingDataAdapter<PagingUserResponse, HomeAdapter.HomeViewHolder>(diffCallback) {

    class HomeViewHolder(val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root)

    var listener: HomeItemListener? = null

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PagingUserResponse>() {
            override fun areItemsTheSame(
                oldItem: PagingUserResponse,
                newItem: PagingUserResponse,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PagingUserResponse,
                newItem: PagingUserResponse,
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeItemBinding.inflate(layoutInflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)!!
        with(holder.binding) {
            this.homeText.text = item.name
            this.homeText.setOnClickListener {
                listener?.invoke(item)
            }
        }
    }
}
