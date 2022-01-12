package com.demo.biometric.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.demo.biometric.presentation.view.dialog.LoadingDialog
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : DaggerFragment() {

    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    internal val binding
        get() = _binding as T

    lateinit var baseActivity: BaseActivity<*>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

    internal fun replaceFragment(cls: Class<*>, bundle: Bundle? = null): BaseFragment<*> {
        return baseActivity.replaceFragment(cls, bundle)
    }

    internal fun addFragment(
        cls: Class<*>,
        backStackState: Int = BaseActivity.BACK_STACK_STATE_ADD,
        bundle: Bundle? = null,
        sharedView: View? = null
    ): BaseFragment<*> {
        return baseActivity.addFragment(cls, backStackState, bundle, sharedView)
    }

    fun backPressed() {
        baseActivity.onBackPressed()
    }

    open fun onBackPressed() {
        baseActivity.backPressed()
    }

    private var loadingDialog: LoadingDialog? = null
    internal fun setLoading(show: Boolean) {
        if (show) {
            if (loadingDialog?.isShowing() == true)
                return

            loadingDialog = LoadingDialog.instance()
            loadingDialog?.show(childFragmentManager, null)

        } else {
            if (loadingDialog?.isShowing() == true)
                loadingDialog?.dismiss()
        }
    }
}