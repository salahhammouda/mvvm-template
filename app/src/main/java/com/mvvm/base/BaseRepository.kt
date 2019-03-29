package com.mvvm.base


import com.mvvm.data.db.Database
import com.mvvm.data.retrofit.APIClient
import com.mvvm.global.helper.SharedPreferences

/**
 * this is the base repository class, all project repositories should extends this class.
 */
abstract class BaseRepository(protected val apiClient: APIClient, protected val sharedPreferences: SharedPreferences, protected val database: Database)
