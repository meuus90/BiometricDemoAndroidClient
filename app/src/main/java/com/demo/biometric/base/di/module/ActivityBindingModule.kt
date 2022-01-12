package com.demo.biometric.base.di.module

import com.demo.biometric.base.di.annotation.ActivityScope
import com.demo.biometric.presentation.view.activity.MainActivity
import com.demo.biometric.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
abstract class ActivityBindingModule {

    @ExperimentalCoroutinesApi
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainViewModel.VMModule::class,
            FragmentBindingModule::class
        ]
    )
    abstract fun mainActivity(): MainActivity
}