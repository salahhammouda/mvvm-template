package com.mvvm.di.component


import android.app.Application
import com.mvvm.MVVMApplication
import com.mvvm.di.ApplicationScope
import com.mvvm.di.contribute.ContributeActivityModule
import com.mvvm.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule


@ApplicationScope
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, ContributeActivityModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance

        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: MVVMApplication)

}
