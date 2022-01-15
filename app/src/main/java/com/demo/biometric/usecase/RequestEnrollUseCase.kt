package com.demo.biometric.usecase

import com.demo.biometric.base.network.Outcome
import com.demo.biometric.data.Params
import com.demo.biometric.data.remote.repository.RequestEnrollRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestEnrollUseCase
@Inject
constructor(private val repository: RequestEnrollRepository) :
    BaseUseCase<Outcome>() {
    override fun performAction(params: Params): Flow<Outcome> {
        return repository.execute(params)
    }
}