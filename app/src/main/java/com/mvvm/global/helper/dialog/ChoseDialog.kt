package com.mvvm.global.helper.dialog

import android.content.Context
import androidx.annotation.StringRes


class ChoseDialog private constructor(
        val title: String? = null,
        val message: String,
        val ok: String,
        val cancel: String,
        val okActionBlock: (() -> Unit)? = null,
        val cancelActionBlock: (() -> Unit)? = null,
        val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {
        fun build(
                title: String? = null, message: String,
                ok: String, cancel: String, okActionBlock: (() -> Unit)? = null, cancelActionBlock: (() -> Unit)? = null,
                dismissActionBlock: (() -> Unit)? = null
        ): ChoseDialog {
            return ChoseDialog(title, message, ok, cancel, okActionBlock, cancelActionBlock, dismissActionBlock);
        }

        fun build(
                context: Context, @StringRes titleId: Int? = null, @StringRes msgId: Int, @StringRes okId: Int,
                @StringRes cancelId: Int,
                okActionBlock: (() -> Unit)? = null,
                cancelActionBlock: (() -> Unit)? = null,
                dismissActionBlock: (() -> Unit)? = null
        ): ChoseDialog {
            val title = titleId?.let { context.getString(it) }
            return ChoseDialog(
                    title,
                    context.getString(msgId),
                    context.getString(okId),
                    context.getString(cancelId),
                    okActionBlock,
                    cancelActionBlock,
                    dismissActionBlock
            );
        }
    }
}