package com.demo.biometric.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.demo.biometric.R

abstract class BaseFullScreenDialogFragment<T : ViewBinding> : DialogFragment() {
    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    internal val binding
        get() = _binding as T

    lateinit var baseActivity: BaseActivity<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = _binding ?: bindingInflater.invoke(inflater, container, false)

        return requireNotNull(_binding).root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity<*>)
            baseActivity = context
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
//        setHasOptionsMenu(true)
    }
}