package com.mvvm.data.repository.imp

import androidx.lifecycle.LiveData
import com.mvvm.base.BaseRepository
import com.mvvm.data.db.Database
import com.mvvm.data.model.news.News
import com.mvvm.data.repository.abs.NewsRepository
import com.mvvm.data.retrofit.APIClient
import com.mvvm.global.helper.SharedPreferences
import io.reactivex.Single
import javax.inject.Inject

class NewsRepositoryImp @Inject constructor(
    apiClient: APIClient,
    sharedPreferences: SharedPreferences,
    database: Database
) : BaseRepository(apiClient, sharedPreferences, database), NewsRepository {

    override fun getNewsAndCache(page: Int, loadSize: Int): Single<List<News>> {
        return apiClient.getNews(page, loadSize).map {
            if (!it.isEmpty()) database.newsDao().insertAll(*it.toTypedArray())
            it
        }
    }

    override fun getCountSingle(): Single<Int> {
        return database.newsDao().getCountSingle()
    }

    override fun getAllNewsSingle(): Single<List<News>> {
        return database.newsDao().getAllOffersSingle()
    }

    override fun getAllNewsLiveData(): LiveData<List<News>> {
        return database.newsDao().getAllOffersLiveData()
    }


    override fun insertAll(vararg news: News): Single<Unit> {
        return Single.fromCallable {
            database.newsDao().insertAll(*news)
        }
    }

    override fun delete(news: News): Single<Unit> {
        return Single.fromCallable {
            database.newsDao().delete(news)
        }
    }
}