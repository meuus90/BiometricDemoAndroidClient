package com.demo.biometric.base.di.module

import com.demo.biometric.presentation.view.fragment.EnrollFragment
import com.demo.biometric.presentation.view.fragment.HomeFragment
import com.demo.biometric.presentation.view.fragment.VerifyFragment
import com.demo.biometric.presentation.viewmodel.ChallengeViewModel
import com.demo.biometric.presentation.viewmodel.EnrollViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@ExperimentalCoroutinesApi
abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [EnrollViewModel.VMModule::class])
    abstract fun enrollFragment(): EnrollFragment

    @ContributesAndroidInjector(modules = [ChallengeViewModel.VMModule::class])
    abstract fun verifyFragment(): VerifyFragment

    @ContributesAndroidInjector()
    abstract fun homeFragment(): HomeFragment
}