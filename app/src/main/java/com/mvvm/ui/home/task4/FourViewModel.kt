package com.mvvm.ui.home.task4

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.global.helper.Navigation
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.ui.home.news.NewsActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class FourViewModel @Inject
constructor(
    application: Application,
    disposables: CompositeDisposable,
    schedulerProvider: SchedulerProvider,
    @Named(ExtraKeys.FourFragment.FOUR_INJECT_ARG1_KEY) arg1: String,
    @Named(ExtraKeys.FourFragment.FOUR_INJECT_ARG2_KEY) arg2: String

) : BaseAndroidViewModel(application, disposables, schedulerProvider) {

    val result: MutableLiveData<String> = MutableLiveData()

    init {
        result.value = "$arg1 $arg2"
    }


    fun onActionTaskFourToNewsClicked() {
        navigate(Navigation(NewsActivity::class, arrayOf(result.value!!)))
    }
}
