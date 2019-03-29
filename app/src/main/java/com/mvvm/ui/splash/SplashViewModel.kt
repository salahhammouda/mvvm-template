package com.mvvm.ui.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.BuildConfig
import com.mvvm.R
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.data.repository.abs.UserRepository
import com.mvvm.global.enumeration.Optional
import com.mvvm.global.helper.Navigation
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.global.utils.DebugLog
import com.mvvm.global.utils.SPLASH_TIME
import com.mvvm.global.utils.TAG
import com.mvvm.ui.home.HomeActivity
import com.mvvm.ui.signin.SignInActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplashViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        userRepository: UserRepository
) : BaseAndroidViewModel(application, disposables, schedulerProvider) {

    val version: MutableLiveData<String> = MutableLiveData()

    init {

        disposables
                .add(userRepository.isLoggedInWithDelay(SPLASH_TIME)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe { optional ->
                            when (optional) {
                                is Optional.Some -> {
                                    navigate(Navigation(HomeActivity::class, arrayOf(optional.element)))
                                }
                                is Optional.None -> {
                                    navigate(Navigation(SignInActivity::class))
                                }
                            }
                        })


        version.value = "${application.getString(R.string.splash_version)}${BuildConfig.VERSION_NAME}"

    }


    fun onClicked() {
        shownSnackBarMessage(applicationContext.getString(R.string.splash_version_clicked))
        DebugLog.d(TAG, "onClicked")
    }

}
