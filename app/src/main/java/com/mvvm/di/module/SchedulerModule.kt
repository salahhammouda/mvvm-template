package com.mvvm.di.module

import com.mvvm.global.helper.AppSchedulerProvider
import com.mvvm.global.listener.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SchedulerModule {

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }
}
