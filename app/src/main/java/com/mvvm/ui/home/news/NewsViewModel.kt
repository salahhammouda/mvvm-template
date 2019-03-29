package com.mvvm.ui.home.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mvvm.R
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.data.model.news.News
import com.mvvm.data.repository.abs.NewsRepository
import com.mvvm.global.enumeration.State
import com.mvvm.global.helper.Navigation
import com.mvvm.global.listener.OnItemClickedListener
import com.mvvm.global.listener.RetryListener
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.global.listener.ToolBarListener
import com.mvvm.global.utils.ExtraKeys
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

private const val NEWS_PAGE_SIZE = 10
private const val FIRST_PAGE = 0;


class NewsViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider, private val newsRepository: NewsRepository,
        @Named(ExtraKeys.NewsActivity.NEWS_INJECT_ARG1_KEY) val arg1: String
) : BaseAndroidViewModel(application, disposables, schedulerProvider), ToolBarListener, RetryListener, OnItemClickedListener {


    var newsList: LiveData<PagedList<News>>

    var refreshState: MutableLiveData<State> = MutableLiveData()


    private val newsDataSourceFactory: NewsDataSourceFactory =
            NewsDataSourceFactory(disposables, schedulerProvider, newsRepository, FIRST_PAGE)

    init {

        val config = PagedList.Config.Builder().setPageSize(NEWS_PAGE_SIZE)
                .setInitialLoadSizeHint(NEWS_PAGE_SIZE * 2).setEnablePlaceholders(false).build()

        newsList = LivePagedListBuilder<Int, News>(newsDataSourceFactory, config).build()

        shownSnackBarMessage(arg1)
    }

    fun getState(): LiveData<State> = Transformations.switchMap<NewsDataSource, State>(
            newsDataSourceFactory.newsDataSourceLiveData,
            NewsDataSource::state
    )


    fun isEmptyLoading(): LiveData<Boolean> = Transformations.map(getState()) {
        listIsEmpty() && it == State.LOADING
    }

    fun isEmptyError(): LiveData<Boolean> = Transformations.map(getState()) {
        listIsEmpty() && it == State.ERROR
    }


    fun isRefreshing(): LiveData<Boolean> = Transformations.map(refreshState) {
        !listIsEmpty() && it == State.REFRESHING
    }

    override fun onItemClicked(title: String) {
        shownSnackBarMessage(title)
    }

    override fun onRetry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    override fun onStartClicked() {
        navigate(Navigation((Navigation.Back::class)))
    }

    private fun listIsEmpty(): Boolean {
        return newsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    fun onRefresh() {
        updateState(State.REFRESHING)
        compositeDisposable.add(
                newsRepository.getNewsAndCache(FIRST_PAGE, NEWS_PAGE_SIZE)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    newsDataSourceFactory.newsDataSourceLiveData.value?.refreshData(response)
                                },
                                {
                                    updateState(State.DONE)
                                    shownSnackBarMessage(R.string.global_error_refresh_fail)
                                }
                        )
        )
    }


    private fun updateState(state: State) {
        refreshState.value = state
    }
}