package com.demo.biometric.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.demo.biometric.base.di.annotation.ViewModelKey
import com.demo.biometric.data.local.LocalStorage
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

//@Singleton
class MainViewModel
@Inject
constructor(private val localStorage: LocalStorage) : ViewModel() {

    @Module
    abstract class VMModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        internal abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
    }
}