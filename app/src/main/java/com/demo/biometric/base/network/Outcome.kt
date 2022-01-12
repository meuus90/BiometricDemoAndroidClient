package com.demo.biometric.base.network

sealed class Outcome<T> {
    class Progress<T> : Outcome<T>()
    data class Success<T>(val data: T) : Outcome<T>()
    data class Failure<T>(val e: Throwable) : Outcome<T>()
}