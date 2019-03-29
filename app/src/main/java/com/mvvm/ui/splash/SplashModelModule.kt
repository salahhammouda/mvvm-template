package com.mvvm.ui.splash


import androidx.lifecycle.ViewModel
import com.mvvm.di.ViewModelKey
import com.mvvm.di.module.CompositeModule
import com.mvvm.di.module.RepositoryModule
import com.mvvm.di.module.SchedulerModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [RepositoryModule::class, CompositeModule::class, SchedulerModule::class])
abstract class SplashModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}
