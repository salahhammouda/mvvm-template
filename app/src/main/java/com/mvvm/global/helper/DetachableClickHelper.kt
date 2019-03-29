package com.mvvm.global.helper

import android.content.DialogInterface

class DetachableClickHelper private constructor(
        var okActionClick: (() -> Unit)?,
        var cancelActionBlock: (() -> Unit)?,
        var dismissActionBlock: (() -> Unit)?
) : DialogInterface.OnDismissListener, DialogInterface.OnClickListener {

    override fun onDismiss(dialog: DialogInterface?) {
        dismissActionBlock?.invoke()
        okActionClick = null
        cancelActionBlock = null
        dismissActionBlock = null
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                okActionClick?.invoke()
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                dismissActionBlock?.invoke()
            }
        }
    }


    companion object {
        fun wrapClick(
                okActionClick: (() -> Unit)?,
                cancelActionBlock: (() -> Unit)?,
                dismissActionBlock: (() -> Unit)?
        ): DetachableClickHelper {

            return DetachableClickHelper(
                    okActionClick,
                    cancelActionBlock,
                    dismissActionBlock
            )
        }
    }
}

