package com.mvvm.ui.home.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mvvm.data.model.news.News
import com.mvvm.data.repository.abs.NewsRepository
import com.mvvm.global.enumeration.State
import com.mvvm.global.listener.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action


class NewsDataSource(
        private val compositeDisposable: CompositeDisposable,
        private val schedulerProvider: SchedulerProvider,
        private val newsRepository: NewsRepository,
        private val firstPage: Int
) : PageKeyedDataSource<Int, News>() {

    var state: MutableLiveData<State> = MutableLiveData()

    private var retryCompletable: Completable? = null

    private var responseRefresh: List<News>? = null;

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        responseRefresh?.let {
            fetchRefresh(callback, it);
        } ?: run {
            fetchLoadInitial(params, callback)
        }
    }

    private fun fetchLoadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, News>
    ): Boolean {
        updateState(State.LOADING)
        return compositeDisposable.add(
                newsRepository.getNewsAndCache(firstPage, params.requestedLoadSize)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    callback.onResult(
                                            response,
                                            null,
                                            firstPage + 1
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadInitial(params, callback) })
                                }
                        )
        )
    }

    private fun fetchRefresh(
            callback: LoadInitialCallback<Int, News>,
            it: List<News>
    ) {
        callback.onResult(
                it,
                null,
                firstPage + 1
        )
        responseRefresh = null
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                newsRepository.getNewsAndCache(params.key, params.requestedLoadSize)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    callback.onResult(
                                            response,
                                            params.key + 1
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadAfter(params, callback) })
                                }
                        )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    private fun updateState(newState: State) {
        state.postValue(newState)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                    retryCompletable!!
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }


    fun refreshData(response: List<News>) {
        responseRefresh = response
        invalidate()
    }
}