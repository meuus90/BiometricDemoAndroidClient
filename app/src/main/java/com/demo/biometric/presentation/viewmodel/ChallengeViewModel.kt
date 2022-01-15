package com.demo.biometric.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.biometric.base.di.annotation.ViewModelKey
import com.demo.biometric.data.Params
import com.demo.biometric.data.local.LocalStorage
import com.demo.biometric.usecase.RequestChallengeUseCase
import com.demo.biometric.usecase.VerifyChallengeUseCase
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

//@Singleton
class ChallengeViewModel
@Inject
constructor(
    private val requestChallengeUseCase: RequestChallengeUseCase,
    private val verifyChallengeUseCase: VerifyChallengeUseCase,
    private val localStorage: LocalStorage
) : ViewModel() {

    @ExperimentalCoroutinesApi
    val requestResultFlow = requestChallengeUseCase.resultFlow
    fun requestChallenge() {
        viewModelScope.launch {
            val params = Params.init(localStorage.getUUID())
            requestChallengeUseCase.launch(params)
        }
    }

    @ExperimentalCoroutinesApi
    val verifyResultFlow = verifyChallengeUseCase.resultFlow
    fun verifyChallenge(params: Params) {
        viewModelScope.launch {
            params.extra.add(localStorage.getUUID())
            verifyChallengeUseCase.launch(params)
        }
    }

    @Module
    abstract class VMModule {
        @Binds
        @IntoMap
        @ViewModelKey(ChallengeViewModel::class)
        internal abstract fun bindViewModel(viewModel: ChallengeViewModel): ViewModel
    }
}