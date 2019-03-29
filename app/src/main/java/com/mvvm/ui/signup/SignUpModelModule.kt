package com.mvvm.ui.signup


import androidx.lifecycle.ViewModel
import com.mvvm.di.ViewModelKey
import com.mvvm.di.module.CompositeModule
import com.mvvm.di.module.RepositoryModule
import com.mvvm.di.module.SchedulerModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [RepositoryModule::class, CompositeModule::class, SchedulerModule::class])
abstract class SignUpModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSinUpViewModel(viewModel: SignUpViewModel): ViewModel

}
