package com.mvvm.ui.signin

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mvvm.R
import com.mvvm.base.BaseActivity
import com.mvvm.data.model.user.User
import com.mvvm.databinding.ActivitySigninBinding
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.ViewModelFactory
import com.mvvm.global.utils.DebugLog
import com.mvvm.global.utils.ExtraKeys
import com.mvvm.global.utils.TAG
import com.mvvm.ui.home.HomeActivity
import com.mvvm.ui.password.ResetPasswordActivity
import com.mvvm.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_signin.*
import javax.inject.Inject

class SignInActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SignInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySigninBinding>(this, R.layout.activity_signin)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)

        registerBindingAndBaseObservers(binding)
        registerSinInObservers()
    }


    private fun registerSinInObservers() {
        viewModel.showPassword.observe(this, Observer { showPassword(it) })
    }

    /**
     * handling navigation event
     */
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
            ResetPasswordActivity::class -> navigateToActivity(ResetPasswordActivity::class)
            SignUpActivity::class -> navigateToActivity(SignUpActivity::class, true)
            Navigation.Back::class -> finish()
        }
    }

    /**
     * update password visibility state
     */
    private fun showPassword(show: Boolean) {
        if (show) {
            editSigninPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            editSigninPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivitySigninBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }
}
