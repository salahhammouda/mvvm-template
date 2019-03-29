package com.mvvm.data.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.mvvm.data.model.news.News
import io.reactivex.Single


@Dao
interface NewsDao {

    @Query("SELECT count(*) FROM news")
    fun getCount(): Int

    @Query("SELECT count(*) FROM news")
    fun getCountSingle(): Single<Int>

    @Query("SELECT count(*) FROM news")
    fun getCountLiveData(): LiveData<Int>

    @Query("SELECT * from news ORDER BY title")
    fun getAllOffersSingle(): Single<List<News>>

    @Query("SELECT * from news ORDER BY title")
    fun getAllOffersLiveData(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: News)

    @Delete
    fun delete(news: News)

}
