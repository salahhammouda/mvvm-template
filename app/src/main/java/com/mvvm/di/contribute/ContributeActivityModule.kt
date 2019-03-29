package com.mvvm.di.contribute

import com.mvvm.di.ActivityScope
import com.mvvm.ui.home.HomeActivity
import com.mvvm.ui.home.HomeModule
import com.mvvm.ui.home.news.NewsActivity
import com.mvvm.ui.home.news.NewsModule
import com.mvvm.ui.signin.SignInActivity
import com.mvvm.ui.signin.SignInModelModule
import com.mvvm.ui.signup.SignUpActivity
import com.mvvm.ui.signup.SignUpModelModule
import com.mvvm.ui.splash.SplashActivity
import com.mvvm.ui.splash.SplashModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributeActivityModule {


    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModelModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SignInModelModule::class])
    abstract fun contributeSinInActivity(): SignInActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SignUpModelModule::class])
    abstract fun contributeSinUpActivity(): SignUpActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class, ContributeFragmentModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [NewsModule::class])
    abstract fun contributeFiveActivity(): NewsActivity

}
