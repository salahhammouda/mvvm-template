package com.mvvm.global.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.databinding.ItemListFooterBinding
import com.mvvm.global.enumeration.State
import com.mvvm.global.listener.RetryListener


class ListFooterViewHolder(private val binding: ItemListFooterBinding, private val retryListener: RetryListener) : RecyclerView.ViewHolder(binding.root) {

    fun bind(status: State) {
        binding.state = status
        binding.retryListener = retryListener
        binding.executePendingBindings();
    }

    companion object {
        fun create(parent: ViewGroup, retryListener: RetryListener): ListFooterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemListFooterBinding.inflate(inflater, parent, false)
            return ListFooterViewHolder(binding, retryListener)
        }
    }
}