package com.mvvm.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mvvm.R
import com.mvvm.global.helper.DetachableClickHelper
import com.mvvm.global.helper.Navigation
import com.mvvm.global.utils.DebugLog
import com.mvvm.global.utils.observeOnlyNotNull
import com.mvvm.ui.view.CustomProgressDialog
import com.mvvm.ui.view.CustomSnackBar
import com.squareup.picasso.Picasso
import dagger.Lazy
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlin.reflect.KClass


private val TAG = BaseActivity::class.java.simpleName

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    protected lateinit var picassoLazy: Lazy<Picasso>

    private var customSnackBar: CustomSnackBar? = null
    private var progressDialog: CustomProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.non)
    }


    /**
     * observe snackBarMessage and show snack bar
     * observe loader and show hide progress bar
     * observe dialog and show dialog
     *
     * @param viewModel BaseAndroidViewModel
     */
    protected fun registerBaseObservers(viewModel: ViewModel) {
        if (viewModel is BaseAndroidViewModel) {
            registerSnackBar(viewModel)
            registerSimpleDialog(viewModel)
            registerChoseDialog(viewModel)
            registerProgressBar(viewModel)
            registerNavigation(viewModel)
        }
    }

    private fun registerNavigation(viewModel: BaseAndroidViewModel) {
        viewModel.navigation.observe(this, Observer { navigate(it) })
    }


    private fun registerSnackBar(viewModel: BaseAndroidViewModel) {
        viewModel.snackBarMessage.observeOnlyNotNull(this) { showSnackBar(it) }
    }

    private fun registerSimpleDialog(viewModel: BaseAndroidViewModel) {
        viewModel.simpleDialog.observeOnlyNotNull(
                this
        ) { simpleDialog ->
            showSimpleDialog(
                    simpleDialog.title,
                    simpleDialog.message,
                    simpleDialog.okActionBlock,
                    simpleDialog.dismissActionBlock
            )
        }
    }

    private fun registerChoseDialog(viewModel: BaseAndroidViewModel) {
        viewModel.choseDialog.observeOnlyNotNull(
                this
        ) { choseDialog ->
            showChoseDialog(
                    choseDialog.title,
                    choseDialog.message,
                    choseDialog.ok,
                    choseDialog.cancel,
                    choseDialog.okActionBlock,
                    choseDialog.cancelActionBlock,
                    choseDialog.dismissActionBlock
            )
        }
    }

    private fun registerProgressBar(viewModel: BaseAndroidViewModel) {
        viewModel.progressBar.observeOnlyNotNull(this) {
            when {
                it -> showProgressBar()
                else -> hideProgressBar()
            }
        }
    }


    protected fun getPicasso(): Picasso {
        return picassoLazy.get()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        pendingTransition()
    }


    override fun finish() {
        super.finish()
        pendingTransition()
    }


    private fun pendingTransition() {
        overridePendingTransition(R.anim.non, R.anim.slide_out_right)
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
    fun showSimpleDialog(
            @StringRes titleId: Int? = null, @StringRes messageId: Int, okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val title = titleId?.let { getString(it) }
            showSimpleDialog(title, getString(messageId), okActionBlock, dismissActionBlock)
        }
    }


    /**
     * show simple ok dialog
     * @param title  message string optional
     * @param message  message string
     * @param okActionBlock action to do on click
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showSimpleDialog(
            title: String? = null,
            message: String,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val clickListener = getWrapperClick(okActionBlock, null, dismissActionBlock)
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(okActionBlock == null)
                    .setMessage(message)
                    .setPositiveButton(R.string.global_ok, clickListener)
                    .setOnDismissListener(clickListener)
            title?.let { builder.setTitle(it) }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


    /**
     * show simple ok dialog
     *
     * @param titleId      dialog title resources Id optional
     * @param messageId       message resources Id
     * @param okId        action button resources Id
     * @param cancelId     cancel button resources Id
     * @param okActionBlock    action to do on click ok optional
     * @param cancelActionBlock action to do on click cancel optional
     * @param dismissActionBlock action to do on dismiss optional
     */
    fun showChoseDialog(
            @StringRes titleId: Int? = null, @StringRes messageId: Int, @StringRes okId: Int,
            @StringRes cancelId: Int, okActionBlock: (() -> Unit)? = null,
            cancelActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val title = titleId?.let { getString(it) }
            showChoseDialog(
                    title,
                    getString(messageId),
                    getString(okId),
                    getString(cancelId),
                    okActionBlock,
                    cancelActionBlock,
                    dismissActionBlock
            );
        }
    }

    /**
     * show simple ok dialog
     *
     * @param title      dialog title optional
     * @param message       message
     * @param ok        action button
     * @param cancel     cancel button
     * @param okActionBlock     action to do on click ok optional
     * @param cancelActionBlock action to do on click cancel optional
     * @param dismissActionBlock action to do on dismiss optional
     *
     */
    fun showChoseDialog(
            title: String? = null,
            message: String,
            ok: String,
            cancel: String,
            okActionBlock: (() -> Unit)? = null,
            cancelActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ) {
        if (!isFinishing) {
            val listenerWrapper = getWrapperClick(okActionBlock, cancelActionBlock, dismissActionBlock)
            val builder = AlertDialog.Builder(this)
            builder
                    .setCancelable(okActionBlock == null)
                    .setMessage(message)
                    .setPositiveButton(ok, listenerWrapper)
                    .setNegativeButton(cancel, listenerWrapper)
                    .setOnDismissListener(listenerWrapper)
            title?.let { builder.setTitle(it) }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


    @NonNull
    private fun getWrapperClick(
            okActionBlock: (() -> Unit)? = null,
            cancelActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
    ): DetachableClickHelper {
        return DetachableClickHelper.wrapClick(okActionBlock, cancelActionBlock, dismissActionBlock)
    }


    /**
     * try to hide Keyboard from the focused screen
     */
    fun hideKeyboard() {
        try {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            DebugLog.d(TAG, "Could not hide keyboard, window unreachable. " + e.toString())
        }
    }


    /**
     * This method show simple SnackBar
     *
     * @param message message dialog text
     */
    fun showSnackBar(@StringRes message: String) {
        if (!isFinishing) {
            val root = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            customSnackBar = CustomSnackBar.make(root, CustomSnackBar.DURATION).apply {
                setText(message)
                show()
            }
        }
    }


    /**
     * This method show simple Snackbar
     *
     * @param messageId message dialog text id
     */
    fun showSnackBar(@StringRes messageId: Int) {

        showSnackBar(getString(messageId))
    }

    /**
     * hide snackBar if it's on screen
     */
    fun hideSnackBar() {
        if (!isFinishing) {
            customSnackBar?.dismiss()
            customSnackBar = null
        }
    }


    /**
     * show unavailable Network Error
     */
    fun showErrorNetworkDialog() {
        showSimpleDialog(messageId = R.string.global_error_unavailable_network)
    }

    /**
     * show server Error
     */
    fun showErrorServerDialog() {
        showSimpleDialog(messageId = R.string.global_error_server)
    }


    /**
     * show unavailable Network Error
     */
    fun showErrorNetworkSnackBar() {
        showSnackBar(R.string.global_error_unavailable_network)
    }


    /**
     * show blocking progressBar on the root of the activity
     */
    fun showProgressBar() {
        if (!isFinishing) {
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog(this, R.style.ProgressDialogStyle)
                        .apply { setCancelable(false) }

            }
            if (progressDialog?.isShowing != true) {
                progressDialog!!.show()
            }
        }
    }


    /**
     * hide blocking progressBar
     */
    fun hideProgressBar() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    /**
     * startActivity to class
     * @param kClass activity to navigate to
     * @param shouldFinish should finish current activity
     */
    fun navigateToActivity(kClass: KClass<out Activity>, shouldFinish: Boolean = false) {
        startActivity(Intent(this, kClass.java))
        if (shouldFinish) finish()
    }


    /**
     * handling navigation actions
     * @param navigationTo activity to navigate to
     */
    open fun navigate(navigationTo: Navigation) {
        throw RuntimeException("you should override this to handle naviagtion")
    }


    override fun onDestroy() {
        hideProgressBar()
        super.onDestroy()
    }

}