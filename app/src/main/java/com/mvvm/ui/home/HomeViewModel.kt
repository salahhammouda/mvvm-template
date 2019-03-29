package com.mvvm.ui.home

import android.app.Application
import com.mvvm.R
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.data.model.user.User
import com.mvvm.global.helper.Navigation
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.ui.home.task1.OneFragment
import com.mvvm.ui.home.task2.TwoFragment
import com.mvvm.ui.home.task3.ThreeFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        @Named(ExtraKeys.HomeActivity.HOME_INJECT_USER_KEY) val user: User
) :
        BaseAndroidViewModel(application, disposables, schedulerProvider) {

    init {
        shownSnackBarMessage("${applicationContext.getString(R.string.home_hello)}${user.email}")
    }


    fun onActionOneClicked() {
        navigate(Navigation(OneFragment::class, arrayOf(user)))
    }

    fun onActionTwoClicked() {
        navigate(Navigation(TwoFragment::class))
    }

    fun onActionThreeClicked() {
        navigate(Navigation(ThreeFragment::class, arrayOf("Azerty Test ${HomeViewModel::class.java}")))
    }
}
