package com.demo.biometric.base.biometric

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.ECGenParameterSpec


object CryptoUtil {
    private const val ALGORITHM = "SHA256withECDSA"
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val DEFAULT_SECRET_KEY_NAME = "Y0UR$3CR3TK3YN@M3"

    fun getOrCreateSignature(keyName: String = DEFAULT_SECRET_KEY_NAME): Signature {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed

        val signature = Signature.getInstance(ALGORITHM)
        keyStore.getKey(keyName, null)?.let {
            signature.initSign(it as PrivateKey)
            return signature
        }

        val paramsBuilder = KeyGenParameterSpec.Builder(keyName, KeyProperties.PURPOSE_SIGN)
        paramsBuilder.apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    setDigests(KeyProperties.DIGEST_SHA256)
                    setUserAuthenticationRequired(true)
                    setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                    setInvalidatedByBiometricEnrollment(true)
                    setUserAuthenticationParameters(0, KeyProperties.AUTH_BIOMETRIC_STRONG)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q -> {
                    setDigests(KeyProperties.DIGEST_SHA256)
                    setUserAuthenticationRequired(true)
                    setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                    setInvalidatedByBiometricEnrollment(true)
                    setUserAuthenticationValidityDurationSeconds(-1)
                }
                else -> {
                    setDigests(KeyProperties.DIGEST_SHA256)
                    setUserAuthenticationRequired(true)
                    setAlgorithmParameterSpec(ECGenParameterSpec("secp256r1"))
                    setUserAuthenticationValidityDurationSeconds(-1)
                }
            }
        }

        val generator =
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, ANDROID_KEYSTORE)
        generator.initialize(paramsBuilder.build())

        val keyPair = generator.generateKeyPair()
        signature.initSign(keyPair.private)

        return signature
    }

    fun getPublicKey(keyName: String = DEFAULT_SECRET_KEY_NAME): String {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed

        val publicKey = keyStore.getCertificate(keyName).publicKey
        return "-----BEGIN PUBLIC KEY-----\n${
            Base64.encodeToString(
                publicKey.encoded,
                Base64.DEFAULT
            )
        }-----END PUBLIC KEY-----"
    }

    fun signData(plaintext: String, signature: Signature): String {
        signature.update(plaintext.encodeToByteArray())

        return Base64.encodeToString(signature.sign(), Base64.NO_WRAP or Base64.URL_SAFE)
    }
}