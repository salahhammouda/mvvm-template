package com.mvvm.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mvvm.R
import com.mvvm.global.helper.Navigation
import com.mvvm.global.helper.SingleLiveEvent
import com.mvvm.global.helper.dialog.ChoseDialog
import com.mvvm.global.helper.dialog.SimpleDialog
import com.mvvm.global.listener.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.io.IOException


abstract class BaseAndroidViewModel(
        application: Application,
        protected val compositeDisposable: CompositeDisposable,
        protected val schedulerProvider: SchedulerProvider
) : AndroidViewModel(application) {


    //application context for resource access only
    protected val applicationContext = application.applicationContext!!;

    //for blocking progress bar
    val progressBar: MutableLiveData<Boolean> = MutableLiveData()

    //for displaying simple dialog
    val simpleDialog: MutableLiveData<SimpleDialog> = MutableLiveData()

    //for displaying chose dialog
    val choseDialog: MutableLiveData<ChoseDialog> = MutableLiveData()

    //for displaying global snack bar
    val snackBarMessage: SingleLiveEvent<String> = SingleLiveEvent()

    //for navigation events
    val navigation: SingleLiveEvent<Navigation> = SingleLiveEvent()


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


    protected fun shownSnackBarMessage(message: String) {
        snackBarMessage.value = message
    }


    protected fun shownSnackBarMessage(@StringRes messageResourceId: Int) {
        shownSnackBarMessage(applicationContext.getString(messageResourceId))
    }


    private fun setShownBlockProgress(show: Boolean) {
        progressBar.value = show
    }


    protected fun showBlockProgressBar() {
        setShownBlockProgress(true)
    }


    protected fun hideBlockProgressBar() {
        setShownBlockProgress(false)
    }

    /**
     * show simple ok dialog
     *
     * @param titleId  resources id optional
     * @param messageId  resources id
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownSimpleDialog(
            @StringRes titleId: Int? = null, @StringRes messageId: Int, okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        simpleDialog.value =
                SimpleDialog.build(
                        applicationContext,
                        titleId,
                        messageId,
                        okActionBlock,
                        dismissSimpleBuild(dismissActionBlock)
                )
    }


    /**
     * show simple ok dialog
     * @param title  message string optional
     * @param message  message string
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownSimpleDialog(
            title: String? = null,
            message: String,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        simpleDialog.value = SimpleDialog.build(title, message, okActionBlock, dismissSimpleBuild(dismissActionBlock))
    }


    private fun dismissSimpleBuild(dismissActionBlock: (() -> Unit)? = null): () -> Unit {
        return {
            dismissActionBlock?.invoke()
            simpleDialog.value = null
        }
    }


    /**
     * show simple ok dialog
     *
     * @param titleId      dialog title resources Id
     * @param messageId       message resources Id
     * @param okId        action button resources Id
     * @param cancelId     cancel button resources Id
     * @param okActionBlock    action to do on click ok
     * @param cancelActionBlock action to do on click cancel
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownChoseDialog(
            @StringRes titleId: Int? = null, @StringRes messageId: Int, @StringRes okId: Int,
            @StringRes cancelId: Int, okActionBlock: (() -> Unit)? = null,
            cancelActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {

        choseDialog.value =
                ChoseDialog.build(
                        applicationContext,
                        titleId,
                        messageId,
                        okId,
                        cancelId,
                        okActionBlock,
                        cancelActionBlock,
                        dismissChoseBuild(dismissActionBlock)
                )
    }


    /**
     * show simple ok dialog
     *
     * @param title      dialog title
     * @param message       message
     * @param ok        action button
     * @param cancel     cancel button
     * @param okActionBlock     action to do on click ok
     * @param cancelActionBlock action to do on click cancel
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun shownChoseDialog(
            title: String? = null,
            message: String,
            ok: String,
            cancel: String,
            okActionBlock: (() -> Unit)? = null,
            cancelActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        choseDialog.value =
                ChoseDialog.build(
                        title,
                        message,
                        ok,
                        cancel,
                        okActionBlock,
                        cancelActionBlock,
                        dismissChoseBuild(dismissActionBlock)
                )
    }


    private fun dismissChoseBuild(dismissActionBlock: (() -> Unit)? = null): () -> Unit {
        return {
            dismissActionBlock?.invoke()
            choseDialog.value = null
        }
    }


    /**
     * show simple error server ok dialog
     *
     */
    protected fun shownServerErrorSimpleDialog() {
        shownSimpleDialog(messageId = R.string.global_error_server)
    }


    /**
     * show simple error ok dialog
     * @param throwable error
     *
     */
    protected fun handleThrowable(throwable: Throwable) {
        if (throwable is IOException) {
            shownSimpleDialog(messageId = R.string.global_error_unavailable_network)
        }else if(throwable is HttpException){
            when (throwable.code()) {
                //other default handler
                else ->shownServerErrorSimpleDialog()
            }
        }else {
            shownServerErrorSimpleDialog()
        }
    }


    /**
     * used for navigation events
     * @param navigationTo  destination
     *
     */
    fun navigate(navigationTo: Navigation) {
        navigation.value = navigationTo
    }

}
