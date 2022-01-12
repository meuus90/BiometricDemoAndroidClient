package com.demo.biometric.base.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.demo.biometric.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {
    @Provides
    @Singleton
    fun appContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideLocalStorage(context: Context): LocalStorage {
        return LocalStorage(context)
    }

    @Provides
    @Singleton
    fun provideGSon(): Gson {
        return GsonBuilder().create()
    }

//    @Provides
//    @Singleton
//    fun provideRestAPI(gson: Gson, okHttpClient: OkHttpClient): RestAPI {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.restApiServer)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(okHttpClient)
//            .build()
//
//        return retrofit.create(RestAPI::class.java)
//    }
}