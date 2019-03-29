package com.mvvm.ui.home.task2

import androidx.lifecycle.ViewModel
import com.mvvm.di.FragmentScope
import com.mvvm.di.ViewModelKey
import com.mvvm.di.module.CompositeModule
import com.mvvm.di.module.RepositoryModule
import com.mvvm.di.module.SchedulerModule
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module(includes = [RepositoryModule::class, CompositeModule::class, SchedulerModule::class])
class TwoModule {

    @IntoMap
    @Provides
    @ViewModelKey(TwoViewModel::class)
    fun bindTwoViewModel(viewModel: TwoViewModel): ViewModel {
        return viewModel
    }


    @Provides
    @FragmentScope
    fun provideNewsCachedAdapter(picasso: Picasso): NewsAdapter {
        return NewsAdapter(picasso)
    }
}
