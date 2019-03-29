package com.mvvm.global.listener

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler

    fun newThread(): Scheduler
}
