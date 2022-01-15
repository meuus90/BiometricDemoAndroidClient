package com.demo.biometric.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.biometric.base.di.annotation.ViewModelKey
import com.demo.biometric.data.Params
import com.demo.biometric.data.entity.RequestEnrollData
import com.demo.biometric.data.local.LocalStorage
import com.demo.biometric.usecase.RequestEnrollUseCase
import com.demo.biometric.usecase.VerifyEnrollUseCase
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

//@Singleton
class EnrollViewModel
@Inject
constructor(
    private val requestEnrollUseCase: RequestEnrollUseCase,
    private val verifyEnrollUseCase: VerifyEnrollUseCase,
    private val localStorage: LocalStorage
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val requestResultFlow = requestEnrollUseCase.resultFlow
    fun requestEnroll() {
        viewModelScope.launch {
            val params = Params.init(RequestEnrollData(localStorage.getUUID()))
            requestEnrollUseCase.launch(params)
        }
    }

    @ExperimentalCoroutinesApi
    val verifyResultFlow = verifyEnrollUseCase.resultFlow
    fun verifyEnroll(params: Params) {
        viewModelScope.launch {
            verifyEnrollUseCase.launch(params)
        }
    }

    @Module
    abstract class VMModule {
        @Binds
        @IntoMap
        @ViewModelKey(EnrollViewModel::class)
        internal abstract fun bindViewModel(viewModel: EnrollViewModel): ViewModel
    }
}