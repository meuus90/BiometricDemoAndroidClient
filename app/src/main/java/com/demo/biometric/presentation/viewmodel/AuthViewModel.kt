package com.demo.biometric.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.biometric.base.di.annotation.ViewModelKey
import com.demo.biometric.data.Params
import com.demo.biometric.usecase.RequestEnrollUseCase
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

//@Singleton
class AuthViewModel
@Inject
constructor(private val signInUseCase: RequestEnrollUseCase) : ViewModel() {

    @ExperimentalCoroutinesApi
    val signInResult = signInUseCase.resultFlow
    fun signIn(params: Params) {
        viewModelScope.launch {
            signInUseCase.launch(params)
        }
    }

    @Module
    abstract class VMModule {
        @Binds
        @IntoMap
        @ViewModelKey(AuthViewModel::class)
        internal abstract fun bindViewModel(viewModel: AuthViewModel): ViewModel
    }
}