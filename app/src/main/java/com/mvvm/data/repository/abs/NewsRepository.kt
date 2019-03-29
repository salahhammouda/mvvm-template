package com.mvvm.data.repository.abs

import androidx.lifecycle.LiveData
import com.mvvm.data.model.news.News
import io.reactivex.Single

interface NewsRepository {

    fun getNewsAndCache(page: Int, loadSize: Int): Single<List<News>>

    fun getCountSingle(): Single<Int>

    fun getAllNewsSingle(): Single<List<News>>

    fun getAllNewsLiveData(): LiveData<List<News>>

    fun insertAll(vararg news: News): Single<Unit>

    fun delete(news: News): Single<Unit>
}