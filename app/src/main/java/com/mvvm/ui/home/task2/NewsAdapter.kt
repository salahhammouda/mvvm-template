package com.mvvm.ui.home.task2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.data.model.news.News
import com.mvvm.global.listener.DataAdapterListener
import com.mvvm.global.viewholder.NewsViewHolder
import com.squareup.picasso.Picasso

class NewsAdapter(private val picasso: Picasso) : RecyclerView.Adapter<NewsViewHolder>(), DataAdapterListener<List<News>> {

    private val news = arrayListOf<News>();

    lateinit var twoViewModel: TwoViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent, twoViewModel,picasso)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun setData(data: List<News>) {
        news.clear()
        news.addAll(data)
        notifyDataSetChanged()
    }
}
