package com.mvvm.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvvm.data.model.news.News


@Database(entities = [News::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}
