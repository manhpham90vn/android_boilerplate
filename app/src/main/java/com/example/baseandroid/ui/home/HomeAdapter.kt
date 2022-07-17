package com.example.baseandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baseandroid.databinding.HomeItemBinding
import com.example.baseandroid.models.PagingUserResponse

typealias HomeItemListener = (PagingUserResponse) -> Unit

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: HomeItemBinding): RecyclerView.ViewHolder(binding.root)

    private var items: MutableList<PagingUserResponse> = mutableListOf()
    var listener: HomeItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeItemBinding.inflate(layoutInflater, parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            this.homeText.text = item.name
            this.homeText.setOnClickListener {
                listener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun set(item: MutableList<PagingUserResponse>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

}