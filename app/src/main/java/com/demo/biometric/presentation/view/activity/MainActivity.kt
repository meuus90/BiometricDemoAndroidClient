package com.demo.biometric.presentation.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.demo.biometric.databinding.ActivityMainBinding
import com.demo.biometric.presentation.base.BaseActivity
import com.demo.biometric.presentation.view.fragment.EnrollFragment
import com.demo.biometric.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayoutId = binding.contentFrame.id

        replaceFragment(EnrollFragment::class.java)
    }
}