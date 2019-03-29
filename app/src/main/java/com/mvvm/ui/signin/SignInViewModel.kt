package com.mvvm.ui.signin

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.mvvm.R
import com.mvvm.base.BaseAndroidViewModel
import com.mvvm.data.model.user.ProfileResponse
import com.mvvm.data.repository.abs.UserRepository
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.NonNullLiveData
import com.mvvm.global.listener.SchedulerProvider
import com.mvvm.global.listener.ToolBarListener
import com.mvvm.global.utils.*
import com.mvvm.ui.home.HomeActivity
import com.mvvm.ui.password.ResetPasswordActivity
import com.mvvm.ui.signup.SignUpActivity
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response
import javax.inject.Inject


class SignInViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        private val userRepository: UserRepository
) :
        BaseAndroidViewModel(application, disposables, schedulerProvider), ToolBarListener {


    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()


    val showPassword: NonNullLiveData<Boolean> = NonNullLiveData(false)
    val signingIn: NonNullLiveData<Boolean> = NonNullLiveData(false)


    fun onForgetPasswordClicked() {
        navigate(Navigation(ResetPasswordActivity::class))
    }

    fun onSignUpClicked() {
        navigate(Navigation(SignUpActivity::class))
    }

    override fun onStartClicked() {
        navigate(Navigation((Navigation.Back::class)))
    }

    fun onShowPasswordClicked(show: Boolean) {
        showPassword.value = show
    }


    fun onSignInClicked() {
        if (validateFields()) {
            signingIn.value = true
            compositeDisposable.add(
                    userRepository.signInAndCache(email.value!!, password.value!!)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(onSignInSuccess(), onSignInError())
            )
        }
    }


    private fun onSignInSuccess(): (Response<ProfileResponse>) -> Unit = { userResponse ->
        signingIn.value = false
        if (userResponse.isSuccessful && userResponse.body()?.data != null) {
            navigate(Navigation(HomeActivity::class, arrayOf(userResponse.body()!!.data!!)))
        } else {
            when (userResponse.code()) {
                HttpResponseCode.HTTP_UNAUTHORIZED -> shownSimpleDialog(messageId = R.string.signin_invalid_data)
                HttpResponseCode.HTTP_BAD_REQUEST -> shownSimpleDialog(messageId = R.string.global_error_banned_user)
                else -> handleFailStatusCode(userResponse.code())
            }
        }
    }

    private fun onSignInError(): (Throwable) -> Unit = { error ->
        signingIn.value = false
        handleThrowable(error)
    }


    /**
     * Check inputs validity
     */
    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(email.value) || TextUtils.isEmpty(password.value)) {
            shownSimpleDialog(messageId = R.string.global_error_empty)
        } else if (!isValidEmail(email.value)) {
            shownSimpleDialog(messageId = R.string.global_error_invalid_email_format)
        } else if (password.value!!.length < MIN_PASSWORD_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_password_length)
        } else if (password.value!!.length > MAX_PASSWORD_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_password_length_max)
        } else if (email.value!!.length > MAX_EMAIL_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_mail_length_max)
        } else {
            return true
        }
        return false
    }
}
