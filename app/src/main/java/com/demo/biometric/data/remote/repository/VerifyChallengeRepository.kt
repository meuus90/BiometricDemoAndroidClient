package com.demo.biometric.data.remote.repository

import com.demo.biometric.base.network.Outcome
import com.demo.biometric.data.Params
import com.demo.biometric.data.entity.SignedData
import com.demo.biometric.data.remote.api.RestAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyChallengeRepository
@Inject
constructor(private val api: RestAPI) {
    fun execute(params: Params) = flow {
        emit(Outcome.Progress())
        val data = SignedData(params.extra[1] as String, params.extra[0] as String)
        val response = api.verifyChallenge(data)
        emit(Outcome.Success(response))
    }.catch {
        emit(Outcome.Failure(it))
    }.flowOn(Dispatchers.IO)
}