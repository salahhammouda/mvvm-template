package com.mvvm.ui.home.news

import androidx.lifecycle.ViewModel
import com.mvvm.di.ActivityScope
import com.mvvm.di.ViewModelKey
import com.mvvm.di.module.CompositeModule
import com.mvvm.di.module.RepositoryModule
import com.mvvm.di.module.SchedulerModule
import com.mvvm.global.utils.ExtraKeys
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named


@Module(includes = [RepositoryModule::class, CompositeModule::class, SchedulerModule::class])
class NewsModule {

    @IntoMap
    @Provides
    @ViewModelKey(NewsViewModel::class)
    fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel {
        return viewModel
    }


    @Provides
    @Named(ExtraKeys.NewsActivity.NEWS_INJECT_ARG1_KEY)
    fun provideVar(newsActivity: NewsActivity): String {
        return NewsActivityArgs.fromBundle(newsActivity.intent.extras!!).var1;
    }


    @Provides
    @ActivityScope
    fun provideNewsPaginateAdapter(picasso:Picasso): NewsListAdapter {
        return NewsListAdapter(picasso)
    }
}
