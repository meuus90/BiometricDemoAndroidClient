package com.demo.biometric

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import com.bumptech.glide.Glide
import com.demo.biometric.base.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class DemoApp : DaggerApplication(), LifecycleObserver {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.uprootAll()
            Timber.plant(Timber.DebugTree())
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        registerComponentCallbacks(AppComponentCallback(this))
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    class AppComponentCallback(private val app: DemoApp) : ComponentCallbacks2 {
        override fun onConfigurationChanged(p0: Configuration) {}

        override fun onTrimMemory(level: Int) {
            Glide.get(app).trimMemory(level)
        }

        override fun onLowMemory() {
            Timber.d("onLowMemory")

            Glide.get(app).clearMemory()
        }
    }
}