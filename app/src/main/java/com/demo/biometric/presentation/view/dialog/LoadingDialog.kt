package com.demo.biometric.presentation.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.demo.biometric.databinding.DialogLoadingBinding
import com.demo.biometric.presentation.base.BaseFullScreenDialogFragment

class LoadingDialog() : BaseFullScreenDialogFragment<DialogLoadingBinding>() {
    companion object {
        fun instance() = LoadingDialog()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogLoadingBinding
        get() = DialogLoadingBinding::inflate

    fun isShowing(): Boolean {
        return if (dialog != null) {
            dialog!!.isShowing
        } else {
            dismiss()
            false
        }
    }
}