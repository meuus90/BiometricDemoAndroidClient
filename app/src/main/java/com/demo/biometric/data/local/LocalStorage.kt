package com.demo.biometric.data.local

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage
@Inject
constructor(val context: Context) {
    companion object {
        internal const val USER_FILE = "User"

        const val key_auth_token = "auth_token"
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(USER_FILE, Activity.MODE_PRIVATE)

    internal fun clearCache() {
        cleanPreference(clearRoom = true)
    }

    internal fun cleanPreference(clearRoom: Boolean = true) {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

//    internal fun getAuthToken(): String? {
//        val gson = Gson()
//        val json = pref.getString(key_auth_token, "")
//
//        return gson.fromJson(json, String::class.java)
//    }
//
//    internal fun setAuthToken(authToken: String) {
//        val editor = pref.edit()
//        val gson = Gson()
//        val json = gson.toJson(authToken)
//        editor.putString(key_auth_token, json)
//        editor.apply()
//    }
}