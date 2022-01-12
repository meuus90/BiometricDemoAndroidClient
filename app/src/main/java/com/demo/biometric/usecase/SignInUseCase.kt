package com.demo.biometric.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.demo.biometric.base.network.Outcome
import com.demo.biometric.data.Params
import com.demo.biometric.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase
@Inject
constructor() : BaseUseCase<Outcome<Boolean>>() {
    override fun performAction(params: Params): Flow<Outcome<Boolean>> {
        val l = MutableLiveData<Outcome<Boolean>>(Outcome.Progress())
        val token = params.extra[0] as String

        l.value = Outcome.Success(true)
        return l.asFlow()
    }
}