package com.mvvm.ui.home.task2

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.R
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.data.repository.abs.NewsRepository
import com.mvvm.data.repository.abs.TimeRepository
import com.mvvm.global.helper.Navigation
import com.mvvm.global.listener.OnItemClickedListener
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.ui.home.task4.FourFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TwoViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        private val timeRepository: TimeRepository,
        newsRepository: NewsRepository
) : BaseAndroidViewModel(application, disposables, schedulerProvider), OnItemClickedListener {


    val time: MutableLiveData<String> = MutableLiveData()

    //using live data to keep list up to date
    val news = newsRepository.getAllNewsLiveData()


    init {
        startCount()
    }

    private fun startCount(
    ) {
        compositeDisposable.add(
                timeRepository.timer()
                        .subscribeOn(schedulerProvider.computation())
                        .observeOn(schedulerProvider.ui()).subscribe(
                                onCountSuccess(), onCountFail(), onComplete()
                        )
        )
    }

    private fun onComplete(): (() -> Unit) = {
        shownChoseDialog(
                messageId = R.string.home_count_completed,
                okId = R.string.global_ok,
                cancelId = R.string.global_cancel,
                okActionBlock = {
                    startCount()
                })
    }

    private fun onCountFail(): (Throwable) -> Unit = {
        shownSimpleDialog(messageId = R.string.home_count_fail)
    }


    private fun onCountSuccess(): (Long) -> Unit = { count ->
        time.value = "hello $count"
    }


    override fun onItemClicked(value: String) {
        shownSnackBarMessage(value)
    }


    fun onActionTaskTwoToFourClicked() {
        navigate(Navigation(FourFragment::class, arrayOf(time.value.toString(), TwoViewModel::class.java.toString())))
    }
}
