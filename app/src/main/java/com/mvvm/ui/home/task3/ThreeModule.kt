package com.mvvm.ui.home.task3

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
class ThreeModule {

    @IntoMap
    @Provides
    @ViewModelKey(ThreeViewModel::class)
    fun bindThreeViewModel(viewModel: ThreeViewModel): ViewModel {
        return viewModel
    }


    @Provides
    @Named(ExtraKeys.ThreeFragment.THREE_INJECT_ARG_KEY)
    fun provideArg(fragment: ThreeFragment): String {
        return fragment.arguments!!.getString(ExtraKeys.ThreeFragment.THREE_EXTRA_ARG_KEY)!!;
    }
}
