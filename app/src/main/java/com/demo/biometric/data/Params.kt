package com.demo.biometric.data

data class Params(val extra: List<Any>) {
    companion object {
        fun init(vararg extra: Any) = Params(extra.toList())
        fun initEmpty() = Params(listOf())
    }
}