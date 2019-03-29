package com.mvvm.data.repository.imp

import com.mvvm.base.BaseRepository
import com.mvvm.data.db.Database
import com.mvvm.data.repository.abs.TimeRepository
import com.mvvm.data.retrofit.APIClient
import com.mvvm.global.helper.SharedPreferences
import com.mvvm.global.utils.TIMER_PERIOD_SECOND
import com.mvvm.global.utils.TIMER_TIME_SECOND
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TimeRepositoryImp @Inject constructor(
        apiClient: APIClient,
        sharedPreferences: SharedPreferences,
        database: Database
) :
        BaseRepository(apiClient, sharedPreferences, database), TimeRepository {

    override fun timer(): Observable<Long> {
        return Observable.interval(TIMER_PERIOD_SECOND, TimeUnit.SECONDS)
                .take(TIMER_TIME_SECOND, TimeUnit.SECONDS)
    }
}
