package com.mvvm.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mvvm.R
import com.mvvm.base.BaseActivity
import com.mvvm.data.model.user.User
import com.mvvm.databinding.ActivitySplashBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.global.utils.DebugLog
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.global.utils.TAG
import com.mvvm.ui.home.HomeActivity
import com.mvvm.ui.signin.SignInActivity
import javax.inject.Inject


class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)

        registerBindingAndBaseObservers(binding)
        registerLoggedInObservers()
    }

    private fun registerLoggedInObservers() {
        //TODO
    }


    override fun navigate(it: Navigation) {
        when (it.navigateTo) {
            HomeActivity::class -> {
                DebugLog.d(TAG, "User is loggedIn value is ${it.extra[0]}")
                navigateToHome(it.extra[0] as User)
            }
            SignInActivity::class -> {
                DebugLog.d(TAG, "User is loggedOut")
                navigateToActivity(SignInActivity::class, true)
            }
        }
    }


    private fun navigateToHome(user: User) {
        Intent(this, HomeActivity::class.java).let {
            it.putExtra(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY, user)
            startActivity(it)
            finish()
        }
    }


    /**
     * Register the UI for XMLBinding
     * Register the activity for base observer
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySplashBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}
