package com.demo.biometric.base.biometric

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import timber.log.Timber


object BiometricUtil {
    interface BiometricAuthListener {
        fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
        fun onBiometricAuthenticationError(errorCode: Int, errString: String)
    }

    fun setBiometricPromptInfo(
        title: String,
        subtitle: String,
        description: String
    ): BiometricPrompt.PromptInfo {
        val builder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText("cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)

        return builder.build()
    }

    fun initBiometricPrompt(
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onBiometricAuthenticationError(errorCode, errString.toString())
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Timber.w("Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onBiometricAuthenticationSuccess(result)
            }
        }

        return BiometricPrompt(activity, executor, callback)
    }

    fun showBiometricPrompt(
        title: String = "Biometric Authentication",
        subtitle: String = "Enter biometric credentials to proceed.",
        description: String = "Input your Fingerprint or FaceID\nto ensure it's you!",
        activity: AppCompatActivity,
        listener: BiometricAuthListener
    ) {
        val promptInfo = setBiometricPromptInfo(title, subtitle, description)

        val biometricPrompt = initBiometricPrompt(activity, listener)

        biometricPrompt.authenticate(
            promptInfo, BiometricPrompt.CryptoObject(
                CryptoUtil.getOrCreateSignature()
            )
        )
    }
}