package com.mvvm.ui.home.news

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.mvvm.R
import com.mvvm.base.BaseActivity
import com.mvvm.databinding.ActivityNewsBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import kotlinx.android.synthetic.main.activity_news.*
import javax.inject.Inject


class NewsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var newsListAdapter: NewsListAdapter


    private lateinit var viewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityNewsBinding>(this, R.layout.activity_news)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)

        registerBindingAndBaseObservers(binding)
        registerNewsObservers()
    }


    /**
     * Register the UI for XML Binding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityNewsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

    /**
     * register UI news Observers
     */
    private fun registerNewsObservers() {
        registerRecycler()
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigation: Navigation) {
        when (navigation.navigateTo) {
            Navigation.Back::class -> finish()
        }
    }

    private fun registerRecycler() {
        newsListAdapter.viewModel = viewModel
        recyclerNews.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerNews.adapter = newsListAdapter
    }
}
