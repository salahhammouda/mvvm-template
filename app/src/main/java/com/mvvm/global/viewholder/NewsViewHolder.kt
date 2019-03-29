package com.mvvm.global.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.R
import com.mvvm.data.model.news.News
import com.mvvm.databinding.ItemNewsBinding
import com.mvvm.global.listener.OnItemClickedListener
import com.squareup.picasso.Picasso

class NewsViewHolder(private val binding: ItemNewsBinding, private val onItemClickedListener: OnItemClickedListener,private val picasso: Picasso) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: News) {
        binding.picasso=picasso
        binding.title = news.title
        binding.imageUrl = news.image
        binding.onItemClickedListener = onItemClickedListener
        binding.placeHolder = AppCompatResources.getDrawable(binding.root.context, R.mipmap.ic_launcher)
    }

    companion object {
        fun create(parent: ViewGroup, onItemClickedListener: OnItemClickedListener,picasso: Picasso): NewsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemNewsBinding.inflate(inflater, parent, false)
            return NewsViewHolder(binding, onItemClickedListener,picasso)
        }
    }
}