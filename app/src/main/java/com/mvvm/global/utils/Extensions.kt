package com.mvvm.global.utils

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.MVVMApplication
import com.mvvm.R
import com.mvvm.global.enumeration.State
import com.mvvm.global.listener.DataAdapterListener
import com.mvvm.global.listener.PaginationStateListener
import com.squareup.picasso.Picasso

/**
 * observe non null live data update
 *
 * @param owner
 * @param observer
 *
 */
fun <T> LiveData<T>.observeOnlyNotNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

/**
 * observe All live data update
 *
 * @param owner
 * @param observer
 *
 */
fun <T> LiveData<T>.observeAll(owner: LifecycleOwner, observer: (t: T?) -> Unit) {
    this.observe(owner, Observer {
        observer(it)
    })
}


/**
 * give default value for the live data
 *
 */
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }


/**
 * property TAG extension for Loging
 *
 */
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }





@BindingAdapter(value = ["imageUrl", "placeholder", "picasso"], requireAll = true)
fun setImageUrl(imageView: ImageView, imageUrl: String, placeHolder: Drawable, picasso: Picasso) {
    if (TextUtils.isEmpty(imageUrl)) {
        imageView.setImageDrawable(placeHolder);
    } else {
        when (imageView.scaleType) {
            ImageView.ScaleType.CENTER_CROP -> picasso.load(imageUrl).fit().centerCrop().placeholder(
                    placeHolder
            ).into(imageView)
            ImageView.ScaleType.CENTER_INSIDE -> picasso.load(imageUrl).fit().centerInside().placeholder(
                    placeHolder
            ).into(imageView)
            else -> picasso.load(imageUrl).placeholder(R.mipmap.ic_launcher).into(imageView)
        }
    }
}


@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T?) {
    data?.let {
        if (recyclerView.adapter is DataAdapterListener<*>) {
            (recyclerView.adapter as DataAdapterListener<T>).setData(it)
        }
    }
}


@BindingAdapter("pagedListAdapterData")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: PagedList<T>?) {
    data?.let {
        if (recyclerView.adapter is PagedListAdapter<*, *>) {
            (recyclerView.adapter as PagedListAdapter<T, *>).submitList(it)
        }
    }
}


@BindingAdapter("pagedListAdapterState")
fun setRecyclerViewProperties(recyclerView: RecyclerView, state: State?) {
    if (recyclerView.adapter is PaginationStateListener) {
        (recyclerView.adapter as PaginationStateListener).setState(state ?: State.DONE)
    }
}
