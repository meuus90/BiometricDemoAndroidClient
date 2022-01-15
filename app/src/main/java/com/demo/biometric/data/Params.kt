package com.demo.biometric.data

data class Params(val extra: MutableList<Any>) {
    companion object {
        fun init(vararg extra: Any) = Params(extra.toMutableList())
        fun initEmpty() = Params(mutableListOf())
    }
}