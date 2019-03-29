package com.mvvm.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mvvm.R
import com.mvvm.base.BaseActivity
import com.mvvm.data.model.user.User
import com.mvvm.databinding.ActivitySignupBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.global.utils.DebugLog
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.global.utils.TAG
import com.mvvm.ui.home.HomeActivity
import com.mvvm.ui.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_signup.*
import javax.inject.Inject

class SignUpActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SignUpViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySignupBinding>(this, R.layout.activity_signup)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        textSignupNotice.movementMethod = LinkMovementMethod.getInstance();

        registerBindingAndBaseObservers(binding)
        registerSinInObservers()

    }

    private fun registerSinInObservers() {
        //TODO
    }


    private fun navigateToHome(user: User) {
        Intent(this, HomeActivity::class.java).let {
            it.putExtra(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY, user)
            startActivity(it)
            finish()
        }
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigation: Navigation) {
        when (navigation.navigateTo) {
            HomeActivity::class -> {
                DebugLog.d(TAG, "User is loggedIn value is ${navigation.extra[0]}")
                navigateToHome(navigation.extra[0] as User)
            }
            Navigation.Back::class -> finish()
            SignInActivity::class -> navigateToActivity(SignInActivity::class, true)
        }
    }


    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySignupBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}
