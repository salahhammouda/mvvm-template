package com.mvvm.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.mvvm.R
import com.mvvm.global.listener.ToolBarListener
import kotlinx.android.synthetic.main.custom_toolbar.view.*


@BindingMethods(BindingMethod(type = CustomToolbar::class, attribute = "app:onActionClicked", method = "setToolBarListener"))
class CustomToolbar : FrameLayout, View.OnClickListener {

    private var toolBarListener: ToolBarListener? = null

    val endActionBtn: AppCompatTextView
        get() {
            return textToolBarAction
        }

    val startActionBtn: AppCompatTextView
        get() {
            return textToolBarStartAction
        }

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_toolbar, this, true)

        layoutToolBarStart.setOnClickListener(this)
        layoutToolBarEnd.setOnClickListener(this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.toolbar_style)

        if (typedArray != null) {

            initBackground(typedArray)

            val hasTitle = typedArray.getBoolean(R.styleable.toolbar_style_ct_has_title, false)
            if (hasTitle) {
                layoutToolBarCenter.visibility = View.VISIBLE
                initTitle(typedArray)
            } else {
                layoutToolBarCenter.visibility = View.GONE
            }

            initStartAction(typedArray)
            initEndAction(typedArray)

            typedArray.recycle()
        }
    }

    private fun initStartAction(typedArray: TypedArray) {

        val actionString = typedArray.getString(R.styleable.toolbar_style_ct_start_action_text)
        val actionTextColor = typedArray.getColor(R.styleable.toolbar_style_ct_start_action_text_color, Color.WHITE)
        val actionTextFont = typedArray.getString(R.styleable.toolbar_style_ct_start_action_text_font)

        val actionDrawable = typedArray.getDrawable(R.styleable.toolbar_style_ct_start_action_drawable)


        if (TextUtils.isEmpty(actionString) && actionDrawable == null) {
            layoutToolBarStart.visibility = View.GONE
        } else {
            layoutToolBarStart.visibility = View.VISIBLE

            if (TextUtils.isEmpty(actionString)) {
                imageToolBarStartAction.visibility = View.VISIBLE
                textToolBarStartAction.visibility = View.GONE
                imageToolBarStartAction.setImageDrawable(actionDrawable)
            } else {
                imageToolBarStartAction.visibility = View.GONE
                textToolBarStartAction.visibility = View.VISIBLE

                textToolBarStartAction.text = actionString
                textToolBarStartAction.setTextColor(actionTextColor)
                textToolBarStartAction.typeface = Typeface.create(actionTextFont, Typeface.BOLD)
            }
        }
    }

    fun showEndActionBtn() {
        textToolBarAction.visibility = View.VISIBLE
        imageToolBarAction.visibility = View.VISIBLE
    }

    fun showStartActionBtn() {
        textToolBarStartAction.visibility = View.VISIBLE
        imageToolBarStartAction.visibility = View.VISIBLE
    }

    fun hideStartActionBtn() {
        textToolBarStartAction.visibility = View.GONE
        imageToolBarStartAction.visibility = View.GONE
    }

    fun hideEndActionBtn() {
        textToolBarAction.visibility = View.GONE
        imageToolBarAction.visibility = View.GONE
    }


    private fun initEndAction(typedArray: TypedArray) {

        val actionString = typedArray.getString(R.styleable.toolbar_style_ct_end_action_text)
        val actionTextColor = typedArray.getColor(R.styleable.toolbar_style_ct_end_action_text_color, Color.WHITE)
        val actionTextFont = typedArray.getString(R.styleable.toolbar_style_ct_end_action_text_font)
        val actionDrawable = typedArray.getDrawable(R.styleable.toolbar_style_ct_end_action_drawable)


        if (TextUtils.isEmpty(actionString) && actionDrawable == null) {
            layoutToolBarEnd.visibility = View.GONE
        } else {
            layoutToolBarEnd.visibility = View.VISIBLE

            if (TextUtils.isEmpty(actionString)) {
                imageToolBarAction.visibility = View.VISIBLE
                textToolBarAction.visibility = View.GONE
                imageToolBarAction.setImageDrawable(actionDrawable)
            } else {
                imageToolBarAction.visibility = View.GONE
                textToolBarAction.visibility = View.VISIBLE
                textToolBarAction.text = actionString
                textToolBarAction.setTextColor(actionTextColor)
                textToolBarAction.typeface = Typeface.create(actionTextFont, Typeface.BOLD)
            }
        }
    }

    private fun initTitle(typedArray: TypedArray) {

        val title = typedArray.getString(R.styleable.toolbar_style_ct_title_text)
        val titleColor = typedArray.getColor(R.styleable.toolbar_style_ct_title_color, Color.BLACK)

        val icon = typedArray.getDrawable(R.styleable.toolbar_style_ct_title_drawable)

        if (!TextUtils.isEmpty(title)) {
            imageToolBarTitle.visibility = View.VISIBLE
            imageToolBarTitle.visibility = View.GONE
            textToolBarTitle.text = title
            textToolBarTitle.setTextColor(titleColor)

        } else {
            imageToolBarTitle.visibility = View.GONE
            imageToolBarTitle.visibility = View.VISIBLE
            imageToolBarTitle.setImageDrawable(icon)
        }
    }

    private fun initBackground(typedArray: TypedArray) {
        val backgroundColor = typedArray.getColor(R.styleable.toolbar_style_ct_background, Color.RED)
        setBackgroundColor(backgroundColor)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.layoutToolBarStart -> toolBarListener!!.onStartClicked()
            R.id.layoutToolBarEnd -> toolBarListener!!.onEndClicked()
        }
    }

    fun setToolBarListener(toolBarListener: ToolBarListener) {
        this.toolBarListener = toolBarListener
    }
}
