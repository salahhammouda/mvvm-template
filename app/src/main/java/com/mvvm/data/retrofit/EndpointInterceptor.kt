package com.mvvm.data.retrofit

import android.content.Context
import com.mvvm.di.ApplicationContext
import com.mvvm.global.helper.SharedPreferences
import com.mvvm.global.utils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Created by MacBook on 8/28/17.
 */

class EndpointInterceptor(private val preferences: SharedPreferences, @param:ApplicationContext private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (isNetworkAvailable(context)) {
            if (preferences.isConnected()) {
                request = request.newBuilder()
                        .method(request.method(), request.body())
                        .addHeader("Authorization", "Bearer " + preferences.getToken())
                        .build()
            }
        } else {
            throw IOException("No network available")
        }
        val response = chain.proceed(request)
        return response
    }


}
