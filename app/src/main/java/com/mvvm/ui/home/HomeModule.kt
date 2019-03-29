package com.mvvm.ui.home

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
class HomeModule {


    @IntoMap
    @Provides
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel {
        return homeViewModel
    }


    @Provides
    @Named(ExtraKeys.HomeActivity.HOME_INJECT_USER_KEY)
    fun provideUser(mainActivity: HomeActivity): User {
        return mainActivity.intent.getParcelableExtra(ExtraKeys.HomeActivity.HOME_EXTRA_USER_KEY)
    }
}
