package com.mvvm.data.repository.imp

import com.mvvm.base.BaseRepository
import com.mvvm.data.db.Database
import com.mvvm.data.model.user.ProfileResponse
import com.mvvm.data.model.user.User
import com.mvvm.data.repository.abs.UserRepository
import com.mvvm.data.retrofit.APIClient
import com.mvvm.global.enumeration.Optional
import com.mvvm.global.helper.SharedPreferences
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject


class UserRepositoryImp @Inject constructor(
    apiClient: APIClient,
    sharedPreferences: SharedPreferences,
    database: Database
) :
    BaseRepository(apiClient, sharedPreferences, database), UserRepository {

    override fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        option: String
    ): Single<Response<ProfileResponse>> {
        return apiClient.signUpAndCache(email, password, firstName, lastName, option).map {
            if (it.isSuccessful && it.body() != null) {
                sharedPreferences.saveUser(it.body()!!.data)
            }
            it
        }
    }

    override fun signInAndCache(email: String, password: String): Single<Response<ProfileResponse>> {
        return apiClient.signIn(email, password).map {
            if (it.isSuccessful && it.body() != null) {
                sharedPreferences.saveUser(it.body()!!.data)
            }
            it
        }
    }

    override fun isLoggedInWithDelay(delay: Long): Single<Optional<User>> {
        return Single.fromCallable {
            Thread.sleep(delay)
            if (sharedPreferences.isConnected()) {
                Optional.Some(sharedPreferences.getUser())
            } else {
                Optional.None
            }
        }
    }
}
