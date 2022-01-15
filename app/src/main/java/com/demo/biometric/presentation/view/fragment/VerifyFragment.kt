package com.demo.biometric.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.demo.biometric.base.biometric.BiometricUtil
import com.demo.biometric.base.biometric.CryptoUtil
import com.demo.biometric.base.network.Outcome
import com.demo.biometric.data.Params
import com.demo.biometric.data.entity.Challenge
import com.demo.biometric.databinding.FragmentVerifyBinding
import com.demo.biometric.presentation.base.BaseFragment
import com.demo.biometric.presentation.viewmodel.ChallengeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


@ExperimentalCoroutinesApi
class VerifyFragment : BaseFragment<FragmentVerifyBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVerifyBinding
        get() = FragmentVerifyBinding::inflate

    private val viewModel by viewModels<ChallengeViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFingerPrint.setOnClickListener {
            baseActivity.setLoading(true)
            viewModel.requestChallenge()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.requestResultFlow.collectLatest {
                when (it) {
                    is Outcome.Success<*> -> {
                        baseActivity.setLoading(false)
                        showBiometricPrompt(it.data as Challenge)
                    }
                    is Outcome.Progress -> {
                        baseActivity.setLoading(true)
                    }
                    is Outcome.Failure -> {
                        baseActivity.setLoading(false)
                        Timber.e(it.e)
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.verifyResultFlow.collectLatest {
                when (it) {
                    is Outcome.Success<*> -> {
                        baseActivity.setLoading(false)

                        replaceFragment(HomeFragment::class.java)
                    }
                    is Outcome.Progress -> {
                        baseActivity.setLoading(true)
                    }
                    is Outcome.Failure -> {
                        baseActivity.setLoading(false)
                        Timber.e(it.e)
                    }
                }
            }
        }
    }

    fun showBiometricPrompt(challenge: Challenge) {
        BiometricUtil.showBiometricPrompt(
            activity = baseActivity,
            listener = object : BiometricUtil.BiometricAuthListener {
                override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
                    result.cryptoObject?.signature?.let {
                        val signedData = CryptoUtil.signData(challenge.challenge, it)
                        viewModel.verifyChallenge(Params.init(signedData))
                    }
                }

                override fun onBiometricAuthenticationError(
                    errorCode: Int,
                    errString: String
                ) {

                }
            }
        )
    }
}