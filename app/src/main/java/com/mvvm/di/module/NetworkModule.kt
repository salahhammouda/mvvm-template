package com.mvvm.di.module


import android.content.Context
import com.mvvm.di.ApplicationContext
import com.mvvm.di.ApplicationScope
import com.mvvm.di.PicassoClientScope
import com.mvvm.data.retrofit.EndpointInterceptor
import com.mvvm.global.helper.SharedPreferences
import com.mvvm.global.utils.DebugLog
import com.mvvm.global.utils.TAG
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 15
private const val WRITE_TIMEOUT = 15
private const val READ_TIMEOUT = 15

@Module(includes = [ApplicationModule::class, PreferencesModule::class])
class NetworkModule {


    @Provides
    @ApplicationScope
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> DebugLog.d(TAG, message) })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @ApplicationScope
    fun endpointInterceptor(preferences: SharedPreferences, @ApplicationContext context: Context): EndpointInterceptor {
        return EndpointInterceptor(preferences, context)
    }

    @Provides
    @ApplicationScope
    fun okHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            endpointInterceptor: EndpointInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(endpointInterceptor).build()
    }

    @Provides
    @ApplicationScope
    fun cacheFile(@ApplicationContext context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    @ApplicationScope
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, (10 * 1000 * 1000).toLong()) //10MB Cache
    }

    @Provides
    @PicassoClientScope
    @ApplicationScope
    fun okHttpPicassoClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
    }
}
