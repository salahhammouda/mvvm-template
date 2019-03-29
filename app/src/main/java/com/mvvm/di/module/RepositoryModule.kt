package com.mvvm.di.module


import com.mvvm.data.repository.abs.NewsRepository
import com.mvvm.data.repository.abs.TimeRepository
import com.mvvm.data.repository.abs.UserRepository
import com.mvvm.data.repository.imp.NewsRepositoryImp
import com.mvvm.data.repository.imp.TimeRepositoryImp
import com.mvvm.data.repository.imp.UserRepositoryImp
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideProfileRepository(profileImp: UserRepositoryImp): UserRepository

    @Binds
    abstract fun provideTimeRepository(timerImp: TimeRepositoryImp): TimeRepository

    @Binds
    abstract fun provideNewsRepository(newsImp: NewsRepositoryImp): NewsRepository

}
