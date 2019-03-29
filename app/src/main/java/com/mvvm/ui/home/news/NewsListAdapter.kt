package com.mvvm.ui.home.news

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.data.model.news.News
import com.mvvm.global.enumeration.State
import com.mvvm.global.listener.PaginationStateListener
import com.mvvm.global.viewholder.ListFooterViewHolder
import com.mvvm.global.viewholder.NewsViewHolder
import com.squareup.picasso.Picasso

private const val DATA_VIEW_TYPE = 1
private const val FOOTER_VIEW_TYPE = 2


class NewsListAdapter(private val picasso: Picasso) : PagedListAdapter<News, RecyclerView.ViewHolder>(newsDiffCallback), PaginationStateListener {

    private var state = State.LOADING

    lateinit var viewModel: NewsViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            NewsViewHolder.create(parent, viewModel,picasso)
        } else {
            ListFooterViewHolder.create(parent, viewModel)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as NewsViewHolder).bind(getItem(position)!!)
        } else {
            (holder as ListFooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val newsDiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    override fun setState(newState: State) {
        if (super.getItemCount() != 0) {
            state = newState
            notifyItemChanged(super.getItemCount())
        }
    }
}