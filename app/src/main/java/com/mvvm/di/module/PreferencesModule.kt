package com.mvvm.di.module


import android.content.Context
import com.mvvm.di.ApplicationContext
import com.mvvm.di.ApplicationScope
import com.mvvm.global.helper.SharedPreferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

@Module(includes = [ApplicationModule::class, ParsingModule::class])
class PreferencesModule {

    @Provides
    @ApplicationScope
    fun sharedPreferences(@ApplicationContext context: Context, moshi: Moshi): SharedPreferences {
        return SharedPreferences(context, moshi)
    }
}
