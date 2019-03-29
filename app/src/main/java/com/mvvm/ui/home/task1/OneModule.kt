package com.mvvm.ui.home.task1

import androidx.lifecycle.ViewModel
import com.mvvm.di.ViewModelKey
import com.mvvm.di.module.CompositeModule
import com.mvvm.di.module.RepositoryModule
import com.mvvm.di.module.SchedulerModule
import com.mvvm.data.model.user.User
import com.mvvm.global.utils.ExtraKeys
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named


@Module(includes = [RepositoryModule::class, CompositeModule::class, SchedulerModule::class])
class OneModule {


    @IntoMap
    @Provides
    @ViewModelKey(OneViewModel::class)
    fun bindOneViewModel(viewModel: OneViewModel): ViewModel {
        return viewModel
    }


    @Provides
    @Named(ExtraKeys.OneFragment.ONE_INJECT_USER_KEY)
    fun provideUser(fragment: OneFragment): User {
        return fragment.arguments!!.getParcelable(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY)!!;
    }
}
