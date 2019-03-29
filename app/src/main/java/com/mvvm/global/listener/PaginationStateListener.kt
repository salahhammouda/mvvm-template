package com.mvvm.global.listener

import com.mvvm.global.enumeration.State

interface PaginationStateListener {
    fun setState(newState: State)
}
