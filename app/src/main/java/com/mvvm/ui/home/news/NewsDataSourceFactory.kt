package com.mvvm.ui.home.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mvvm.data.model.news.News
import com.mvvm.data.repository.abs.NewsRepository
import com.mvvm.global.listener.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
        private val compositeDisposable: CompositeDisposable,
        private val schedulerProvider: SchedulerProvider,
        private val newsRepository: NewsRepository,
        private val firstPage: Int
) : DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(compositeDisposable, schedulerProvider, newsRepository, firstPage)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}