package com.mvvm.data.retrofit

import com.mvvm.data.model.news.News
import com.mvvm.data.model.user.ProfileResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Created on 2/2/18.
 */

interface APIClient {

    @FormUrlEncoded
    @POST("medamine/paginate/signin.json")
    fun signIn(@Field("email") email: String, @Field("password") password: String): Single<ProfileResponse>

    @FormUrlEncoded
    @POST("medamine/paginate/signin.json")
    fun signUpAndCache(@Field("email") email: String, @Field("password") password: String, @Field("first_name") first_name: String, @Field("last_name") last_name: String, @Field("opt_in") option: String): Single<ProfileResponse>


    @GET("medamine/paginate/paginate.php")
    fun getNews(@Query("p") page: Int, @Query("pageSize") pageSize: Int): Single<List<News>>

}
