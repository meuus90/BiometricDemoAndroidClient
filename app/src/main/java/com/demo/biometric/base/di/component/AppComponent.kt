package com.demo.biometric.base.di.component

import android.app.Application
import com.demo.biometric.DemoApp
import com.demo.biometric.base.di.module.ActivityBindingModule
import com.demo.biometric.base.di.module.AppModule
import com.demo.biometric.base.di.module.FactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
//        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        FactoryModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<DemoApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
