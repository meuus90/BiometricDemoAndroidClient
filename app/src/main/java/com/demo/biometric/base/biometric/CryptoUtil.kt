package com.demo.biometric.base.biometric

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Signature


object CryptoUtil {
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val DEFAULT_SECRET_KEY_NAME = "Y0UR$3CR3TK3YN@M3"
    private const val KEY_SIZE = 128
    private const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
    private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
    private const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

    fun getOrCreateSignature(keyName: String = DEFAULT_SECRET_KEY_NAME): Signature {
        // 1
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed

        val signature = Signature.getInstance("signing_algorithm")
        keyStore.getKey(keyName, null)?.let {
            signature.initSign(it as PrivateKey)
            return signature
        }

        // 2
        val paramsBuilder = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        )
        paramsBuilder.apply {
            setBlockModes(ENCRYPTION_BLOCK_MODE)
            setEncryptionPaddings(ENCRYPTION_PADDING)
            setKeySize(KEY_SIZE)
            setUserAuthenticationRequired(true) // for use biometric auth
        }

        // 3
        val generator =
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE)
        generator.initialize(paramsBuilder.build())

        val keyPair = generator.generateKeyPair()
        signature.initSign(keyPair.private)

        return signature
    }

    fun getPublicKey(keyName: String = DEFAULT_SECRET_KEY_NAME): String {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null) // Keystore must be loaded before it can be accessed
        return keyStore.getCertificate(keyName).publicKey.toString()
    }

    fun signData(plaintext: String, signature: Signature): ByteArray {
        signature.update(plaintext.encodeToByteArray())
        return signature.sign()
    }

    fun verifySignedData() {
        
    }
}