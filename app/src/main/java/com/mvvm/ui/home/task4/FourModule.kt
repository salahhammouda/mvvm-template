package com.mvvm.ui.home.task4

import androidx.lifecycle.ViewModel
import com.mvvm.di.ViewModelKey
import com.mvvm.di.module.CompositeModule
import com.mvvm.di.module.RepositoryModule
import com.mvvm.di.module.SchedulerModule
import com.mvvm.global.utils.ExtraKeys
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named


@Module(includes = [RepositoryModule::class, CompositeModule::class, SchedulerModule::class])
class FourModule {

    @IntoMap
    @Provides
    @ViewModelKey(FourViewModel::class)
    fun bindThreeViewModel(viewModel: FourViewModel): ViewModel {
        return viewModel
    }


    @Provides
    @Named(ExtraKeys.FourFragment.FOUR_INJECT_ARG1_KEY)
    fun provideArg1(fragment: FourFragment): String {
        return FourFragmentArgs.fromBundle(fragment.arguments!!).var1;
    }

    @Provides
    @Named(ExtraKeys.FourFragment.FOUR_INJECT_ARG2_KEY)
    fun provideArg2(fragment: FourFragment): String {
        return FourFragmentArgs.fromBundle(fragment.arguments!!).var2;
    }
}
