package com.demo.biometric.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.demo.biometric.data.Params
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest

abstract class BaseUseCase<T> {
    private var _trigger = MutableLiveData<Params?>(null)

    @ExperimentalCoroutinesApi
    val resultFlow: Flow<T> = _trigger.asFlow().filterNotNull().flatMapLatest {
        performAction(it)
    }

    abstract fun performAction(params: Params): Flow<T>

    fun launch(params: Params) {
        _trigger.postValue(params)
    }

    fun onClear() {
        _trigger.postValue(null)
    }
}