package com.demo.biometric.base.network

sealed class Outcome {
    class Progress : Outcome()
    data class Success<T>(val data: T) : Outcome()
    data class Failure(val e: Throwable) : Outcome()
}