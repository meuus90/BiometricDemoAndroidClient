package com.demo.biometric.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.annotation.GlideModule
import com.demo.biometric.presentation.view.dialog.LoadingDialog
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.properties.Delegates

abstract class BaseActivity<T : ViewBinding> : DaggerAppCompatActivity() {
    companion object {
        const val BACK_STACK_STATE_ADD = 0
        const val BACK_STACK_STATE_REPLACE = 1
        const val BACK_STACK_STATE_POP_AND_ADD = 2
    }

    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater) -> T

    internal val binding: T
        get() = _binding as T

    var frameLayoutId by Delegates.notNull<Int>()

    internal lateinit var glideRequestManager: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @GlideModule
        glideRequestManager = Glide.with(this)

        _binding = _binding ?: bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    internal fun replaceFragment(cls: Class<*>, bundle: Bundle? = null): BaseFragment<*> {
        return addFragment(cls, BACK_STACK_STATE_REPLACE, bundle)
    }

    internal fun addFragment(
        cls: Class<*>,
        backStackState: Int = BACK_STACK_STATE_ADD,
        bundle: Bundle? = null,
        sharedView: View? = null
    ): BaseFragment<*> {
        val fragment = getFragmentInstance(cls)
        if (bundle != null)
            fragment.arguments = bundle

        supportFragmentManager.apply {
            when (backStackState) {
                BACK_STACK_STATE_REPLACE -> {
                    popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
                BACK_STACK_STATE_POP_AND_ADD -> {
                    popBackStack()
                }
            }
        }

        callFragment(fragment, sharedView)

        return fragment
    }

    private fun getFragmentInstance(cls: Class<*>): BaseFragment<*> {
        var fragment = supportFragmentManager.findFragmentByTag(cls.name)

        fragment?.let {
            supportFragmentManager.beginTransaction().remove(fragment!!).commit()
        }

        fragment = supportFragmentManager.fragmentFactory.instantiate(cls.classLoader!!, cls.name)
                as BaseFragment<*>
        return fragment
    }

    private fun callFragment(
        fragment: BaseFragment<*>,
        sharedView: View? = null
    ) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .apply {
                addToBackStack(fragment.javaClass.name)

                if (sharedView != null)
                    addSharedElement(sharedView, sharedView.transitionName)
                replace(frameLayoutId, fragment, fragment.javaClass.name)
            }.commit()
    }

    protected fun getCurrentFragment(): BaseFragment<*>? {
        val fragmentCount = supportFragmentManager.backStackEntryCount
        if (fragmentCount > 0) {
            val backEntry = supportFragmentManager.getBackStackEntryAt(fragmentCount - 1)
            val f = supportFragmentManager.findFragmentByTag(backEntry.name)
            return if (f != null) f as BaseFragment<*>
            else null
        }

        return null
    }

    fun backPressed() {
        val fragmentStackSize = supportFragmentManager.backStackEntryCount
        if (fragmentStackSize <= 1)
            supportFinishAfterTransition()
        else
            supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        val fragment = getCurrentFragment()
        if (fragment != null) {
            fragment.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    internal fun hideKeyboard() {
        val inputManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        currentFocus?.windowToken?.let {
            inputManager.hideSoftInputFromWindow(
                it,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private var loadingDialog: LoadingDialog? = null
    internal fun setLoading(show: Boolean) {
        if (show) {
            if (loadingDialog?.isShowing() == true)
                return

            loadingDialog = LoadingDialog.instance()
            loadingDialog?.show(supportFragmentManager, null)

        } else {
            if (loadingDialog?.isShowing() == true)
                loadingDialog?.dismiss()
        }
    }
}