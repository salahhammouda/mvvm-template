package com.mvvm.ui.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.mvvm.R

class CustomSnackBar
/**
 * Constructor for the transient bottom bar.
 *
 * @param parent              The parent for this transient bottom bar.
 * @param content             The bg_content view for this transient bottom bar.
 * @param contentViewCallback The bg_content view callback for this transient bottom bar.
 */
protected constructor(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) :
        BaseTransientBottomBar<CustomSnackBar>(parent, content, contentViewCallback) {

    // set text in custom layout
    fun setText(text: CharSequence): CustomSnackBar {
        val textView = getView().findViewById<View>(R.id.textSnackBarContent) as TextView
        textView.text = text
        return this
    }


    @Suppress("DEPRECATION")
    class ContentViewCallback(private val content: View) : BaseTransientBottomBar.ContentViewCallback {

        override fun animateContentIn(delay: Int, duration: Int) {
            // add custom *in animations for your views
            //ViewCompat.setScaleY(content, 0f);
            //ViewCompat.animate(content).scaleY(1f).setDuration(duration).setStartDelay(delay);
        }

        override fun animateContentOut(delay: Int, duration: Int) {
            // add custom *out animations for your views
            //ViewCompat.setScaleY(content, 1f);
            //ViewCompat.animate(content).scaleY(0f).setDuration(duration).setStartDelay(delay);

        }
    }

    companion object {

        const val DURATION = 3000

        fun make(parent: ViewGroup, duration: Int): CustomSnackBar {
            // inflate custom layout
            val inflater = LayoutInflater.from(parent.context)

            val content = inflater.inflate(R.layout.custom_snackbar, parent, false)
            // create snackbar with custom view
            val callback = ContentViewCallback(content)
            val customSnackbar = CustomSnackBar(parent, content, callback)
            customSnackbar.getView().setPadding(0, 0, 0, 0)
            customSnackbar.getView().setBackgroundColor(Color.TRANSPARENT)

            // set snackbar duration
            customSnackbar.duration = duration
            return customSnackbar
        }
    }
}
