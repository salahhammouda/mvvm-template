package com.mvvm.data.repository.abs

import io.reactivex.Observable

interface TimeRepository {

    fun timer(): Observable<Long>
}
