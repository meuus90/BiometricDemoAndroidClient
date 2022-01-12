package com.demo.biometric.base.di.module

import androidx.lifecycle.ViewModelProvider
import com.demo.biometric.base.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}