package com.demo.biometric.usecase.base

import com.demo.biometric.data.Params
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull

abstract class BaseUseCase<T> {
    private val _trigger = MutableStateFlow<Params?>(null)

    @ExperimentalCoroutinesApi
    val resultFlow: Flow<T> = _trigger.mapNotNull { it }.flatMapLatest {
        performAction(it)
    }

    abstract fun performAction(params: Params): Flow<T>

    suspend fun launch(params: Params) {
        _trigger.emit(params)
    }
}