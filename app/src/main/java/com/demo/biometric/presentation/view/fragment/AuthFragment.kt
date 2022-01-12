package com.demo.biometric.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.demo.biometric.databinding.FragmentAuthBinding
import com.demo.biometric.presentation.base.BaseFragment
import com.demo.biometric.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding
        get() = FragmentAuthBinding::inflate

    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}