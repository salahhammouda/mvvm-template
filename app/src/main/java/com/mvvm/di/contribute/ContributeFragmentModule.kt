package com.mvvm.di.contribute

import com.mvvm.di.FragmentScope
import com.mvvm.ui.home.task1.OneFragment
import com.mvvm.ui.home.task1.OneModule
import com.mvvm.ui.home.task2.TwoFragment
import com.mvvm.ui.home.task2.TwoModule
import com.mvvm.ui.home.task3.ThreeFragment
import com.mvvm.ui.home.task3.ThreeModule
import com.mvvm.ui.home.task4.FourFragment
import com.mvvm.ui.home.task4.FourModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributeFragmentModule {


    @FragmentScope
    @ContributesAndroidInjector(modules = [OneModule::class])
    abstract fun contributeOneFragment(): OneFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TwoModule::class])
    abstract fun contributeTwoFragment(): TwoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ThreeModule::class])
    abstract fun contributeThreeFragment(): ThreeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FourModule::class])
    abstract fun contributeFourFragment(): FourFragment

}