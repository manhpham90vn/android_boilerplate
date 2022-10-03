package com.example.baseandroid.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.baseandroid.databinding.HomeItemBinding
import com.example.baseandroid.models.PagingUserResponse

class HomeUserAdapter(private val listUser: MutableList<PagingUserResponse>) :
    RecyclerView.Adapter<HomeUserAdapter.HomeUserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this.listUser.clear()
        notifyDataSetChanged()
    }

    fun addItemList(listUserParam: List<PagingUserResponse>) {
        val oldList = ArrayList(this.listUser)
        this.listUser.addAll(listUserParam)
        applyDiffUtil(oldList)
    }

    fun removeItem(pagingUserResponse: PagingUserResponse) {
        val oldList = ArrayList(this.listUser)
        this.listUser.remove(pagingUserResponse)
        applyDiffUtil(oldList)
    }

    private fun applyDiffUtil(oldList: MutableList<PagingUserResponse>) {
        val diffCallback = PagingUserResponseDiffCallback(oldList = oldList, newList = this.listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    class HomeUserViewHolder(val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(pagingUserResponse: PagingUserResponse) {
            with(binding) {
                this.homeText.text = pagingUserResponse.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeUserViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeUserViewHolder, position: Int) {
        val itemUser = this.listUser[position]
        holder.setData(itemUser)
    }

    override fun getItemCount(): Int = this.listUser.size
}

class PagingUserResponseDiffCallback(
    private val oldList: MutableList<PagingUserResponse>,
    private val newList: MutableList<PagingUserResponse>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = areItemsTheSame(oldItemPosition, newItemPosition)

}