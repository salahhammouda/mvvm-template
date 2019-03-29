package com.mvvm.di.module


import android.content.Context
import com.mvvm.di.ApplicationContext
import com.mvvm.di.ApplicationScope
import com.mvvm.data.db.Database
import com.mvvm.data.db.DatabaseBuilder
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule::class])
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun databaseProvider(@ApplicationContext context: Context): Database {
        return DatabaseBuilder.getBingoDatabase(context)
    }
}
