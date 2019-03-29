package com.mvvm.ui.home.task1

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.data.model.user.User
import com.mvvm.global.helper.Navigation
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.ui.home.task4.FourFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class OneViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        @Named(ExtraKeys.OneFragment.ONE_INJECT_USER_KEY) user: User

) : BaseAndroidViewModel(application, disposables, schedulerProvider) {

    val userData: MutableLiveData<String> = MutableLiveData()

    init {
        userData.value = user.toString()
    }


    fun onActionTaskOneToFourClicked() {
        navigate(Navigation(FourFragment::class, arrayOf(userData.value.toString())))
    }
}
