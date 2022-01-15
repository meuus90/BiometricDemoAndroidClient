package com.demo.biometric.data.local

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStorage
@Inject
constructor(val context: Context) {
    companion object {
        internal const val USER_FILE = "User"

        const val key_uuid = "key_uuid"
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

    internal fun getUUID(): String {
        val gson = Gson()
        val json = pref.getString(key_uuid, "")

        gson.fromJson(json, String::class.java)?.let {
            return it
        }

        val uuid = UUID.randomUUID().toString()
        setUUID(uuid)
        return uuid
    }

    internal fun setUUID(uuid: String) {
        val editor = pref.edit()
        val gson = Gson()
        val json = gson.toJson(uuid)
        editor.putString(key_uuid, json)
        editor.apply()
    }
}