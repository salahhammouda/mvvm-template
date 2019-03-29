package com.mvvm.di

import androidx.lifecycle.ViewModel
import dagger.MapKey

import kotlin.reflect.KClass


@MapKey
@kotlin.annotation.Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
