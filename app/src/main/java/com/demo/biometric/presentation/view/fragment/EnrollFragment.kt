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
import com.demo.biometric.data.entity.EnrollCode
import com.demo.biometric.data.entity.VerifyEnrollData
import com.demo.biometric.databinding.FragmentEnrollBinding
import com.demo.biometric.presentation.base.BaseFragment
import com.demo.biometric.presentation.viewmodel.EnrollViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


@ExperimentalCoroutinesApi
class EnrollFragment : BaseFragment<FragmentEnrollBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEnrollBinding
        get() = FragmentEnrollBinding::inflate

    private val viewModel by viewModels<EnrollViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvFingerPrint.setOnClickListener {
            baseActivity.setLoading(true)
            viewModel.requestEnroll()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.requestResultFlow.collectLatest {
                when (it) {
                    is Outcome.Success<*> -> {
                        baseActivity.setLoading(false)
                        showBiometricPrompt(it.data as EnrollCode)
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

                        replaceFragment(VerifyFragment::class.java)
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

    fun showBiometricPrompt(enrollCode: EnrollCode) {
        BiometricUtil.showBiometricPrompt(
            title = "바이오 인증 등록",
            activity = baseActivity,
            listener = object : BiometricUtil.BiometricAuthListener {
                override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
                    val data = VerifyEnrollData(
                        CryptoUtil.getPublicKey(),
                        enrollCode.enrollCode
                    )
                    viewModel.verifyEnroll(Params.init(data))
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