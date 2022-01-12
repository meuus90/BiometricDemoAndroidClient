package com.demo.biometric.base.di.module

import com.demo.biometric.presentation.view.fragment.AuthFragment
import com.demo.biometric.presentation.viewmodel.AuthViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@ExperimentalCoroutinesApi
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [AuthViewModel.VMModule::class])
    abstract fun authFragment(): AuthFragment
}