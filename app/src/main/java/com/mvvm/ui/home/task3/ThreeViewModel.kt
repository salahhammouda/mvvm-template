package com.mvvm.ui.home.task3

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

class ThreeViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        @Named(ExtraKeys.ThreeFragment.THREE_INJECT_ARG_KEY) arg: String

) : BaseAndroidViewModel(application, disposables, schedulerProvider) {

    val result: MutableLiveData<String> = MutableLiveData()

    init {
        result.value = arg
    }


    fun onActionTaskThreeToNewsClicked() {
        navigate(Navigation(NewsActivity::class, arrayOf(result.value!!)))
    }
}
