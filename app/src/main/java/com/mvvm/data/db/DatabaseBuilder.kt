package com.mvvm.data.db

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    fun getBingoDatabase(context: Context): Database {

        return Room.databaseBuilder(context, Database::class.java, "database")
                .build()
    }
}
