package com.mvvm.ui.signup

import android.app.Application
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.core.content.ContextCompat
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
import com.mvvm.ui.signin.SignInActivity
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject


class SignUpViewModel @Inject
constructor(
        application: Application,
        disposables: CompositeDisposable,
        schedulerProvider: SchedulerProvider,
        private val userRepository: UserRepository
) : BaseAndroidViewModel(application, disposables, schedulerProvider), ToolBarListener {

    val name: MutableLiveData<String> = MutableLiveData()
    val lastName: MutableLiveData<String> = MutableLiveData()

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val notice: MutableLiveData<SpannableString> = MutableLiveData()

    val noticeCheck: NonNullLiveData<Boolean> = NonNullLiveData(false)
    val partnersCheck: NonNullLiveData<Boolean> = NonNullLiveData(false)


    init {

        val partOne = applicationContext.getString(R.string.signup_game_rules_one)
        val partTow = applicationContext.getString(R.string.signup_game_rules_two)
        val partThere = applicationContext.getString(R.string.signup_game_rules_there)

        val fullText = String.format(partOne, partTow, partThere)

        val firstSpanIndex = 0
        val secondSpanIndex = fullText.indexOf(partTow)
        val thirdSpanIndex = fullText.indexOf(partThere)

        val spannable =
                buildSpannableString(fullText, firstSpanIndex, secondSpanIndex, partTow, thirdSpanIndex, partThere)

        notice.value = spannable
    }


    private fun buildSpannableString(
            fullText: String,
            firstSpanIndex: Int,
            secondSpanIndex: Int,
            partTow: String,
            thirdSpanIndex: Int,
            partThere: String
    ): SpannableString {
        val spannable = SpannableString(fullText)

        spannable.setSpan(getNoticeClickableSpan(), firstSpanIndex, secondSpanIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannable.setSpan(
                getGameRulesText(),
                secondSpanIndex,
                secondSpanIndex + partTow.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
                getLegalNoticeText(),
                thirdSpanIndex,
                thirdSpanIndex + partTow.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
                UnderlineSpan(),
                secondSpanIndex,
                secondSpanIndex + partTow.length,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
                UnderlineSpan(),
                thirdSpanIndex,
                thirdSpanIndex + partThere.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.blue_basic)),
                secondSpanIndex,
                secondSpanIndex + partTow.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.blue_basic)),
                thirdSpanIndex,
                thirdSpanIndex + partThere.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }


    override fun onStartClicked() {
        navigate(Navigation(Navigation.Back::class))
    }

    fun onPartnersClicked(checked: Boolean) {
        partnersCheck.value = checked
    }


    fun onSignUpClicked() {
        if (validateFields()) {
            showBlockProgressBar()
            compositeDisposable.add(
                    userRepository.signUp(name.value!!, lastName.value!!, email.value!!, password.value!!, getPotion())
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(onSignInSuccess(), onSignInError())
            )
        }
    }


    private fun onSignInSuccess(): (ProfileResponse) -> Unit = { userResponse ->
        hideBlockProgressBar()
        navigate(Navigation(HomeActivity::class, arrayOf(userResponse.data)))
    }

    private fun onSignInError(): (Throwable) -> Unit = { error ->
        hideBlockProgressBar()
        if(error is HttpException){
            when (error.code()) {
                HttpResponseCode.HTTP_PAYMENT_REQUIRED -> shownSimpleDialog(messageId = R.string.global_error_banned_user)
                else -> handleThrowable(error)
            }
        }else{
            handleThrowable(error)
        }
    }

    fun onSignInClicked() {
        navigate(Navigation(SignInActivity::class))
    }


    private fun getNoticeClickableSpan(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(textView: View) {
                shownSnackBarMessage("getNoticeClickableSpan clicked")
                noticeCheck.value = !noticeCheck.value
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
    }

    private fun getGameRulesText(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                shownSnackBarMessage("getGameRulesText clicked")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
    }

    private fun getLegalNoticeText(): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                shownSnackBarMessage("getLegalNoticeText clicked")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
    }


    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(name.value) || name.value!!.matches(WHITE_SPACE.toRegex()) || TextUtils.isEmpty(lastName.value) || lastName.value!!.matches(
                        WHITE_SPACE.toRegex()
                ) || TextUtils.isEmpty(email.value) || TextUtils.isEmpty(password.value)
        ) {
            shownSimpleDialog(messageId = R.string.global_error_empty)
        } else if (!isValidEmail(email.value)) {
            shownSimpleDialog(messageId = R.string.global_error_invalid_email_format)
        } else if (password.value!!.length < MIN_PASSWORD_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_password_length)
        } else if (name.value!!.length > MAX_PASSWORD_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_first_length)
        } else if (lastName.value!!.length > MAX_PASSWORD_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_last_length)
        } else if (password.value!!.length > MAX_PASSWORD_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_password_length_max)
        } else if (email.value!!.length > MAX_EMAIL_SIZE) {
            shownSimpleDialog(messageId = R.string.global_error_mail_length_max)
        } else if (!noticeCheck.value) {
            shownSimpleDialog(messageId = R.string.global_error_legal_notice)
        } else {
            return true
        }
        return false
    }

    private fun getPotion(): String {
        return if (!partnersCheck.value) RULES_NOT_SELECTED else RULES_SELECTED
    }

}
