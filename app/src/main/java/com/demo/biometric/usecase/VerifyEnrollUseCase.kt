package com.demo.biometric.usecase

import com.demo.biometric.base.network.Outcome
import com.demo.biometric.data.Params
import com.demo.biometric.data.remote.repository.VerifyEnrollRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyEnrollUseCase
@Inject
constructor(private val repository: VerifyEnrollRepository) :
    BaseUseCase<Outcome>() {
    override fun performAction(params: Params): Flow<Outcome> {
        return repository.execute(params)
    }
}