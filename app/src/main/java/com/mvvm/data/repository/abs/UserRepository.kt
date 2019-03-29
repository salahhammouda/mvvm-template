package com.mvvm.data.repository.abs

import com.mvvm.data.model.user.ProfileResponse
import com.mvvm.data.model.user.User
import com.mvvm.global.enumeration.Optional
import io.reactivex.Single
import retrofit2.Response

interface UserRepository {

    fun isLoggedInWithDelay(delay: Long): Single<Optional<User>>

    fun signInAndCache(email: String, password: String): Single<Response<ProfileResponse>>

    fun signUp(firstName: String, lastName: String, email: String, password: String, option: String): Single<Response<ProfileResponse>>
}
