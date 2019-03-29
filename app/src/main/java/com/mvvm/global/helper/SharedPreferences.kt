package com.mvvm.global.helper

import android.content.Context
import android.text.TextUtils
import com.mvvm.data.model.user.User
import com.mvvm.global.utils.*
import com.securepreferences.SecurePreferences
import com.squareup.moshi.Moshi

private const val FILE_NAME_FLAG = "bingo_file_flag"


private const val TOKEN_FLAG = "1"
private const val USER_FLAG = "2"


class SharedPreferences(context: Context, val moshi: Moshi) {

    private val sharedPreferences: android.content.SharedPreferences

    init {
        DebugLog.d(TAG, "SharedPreferences init")
        sharedPreferences = SecurePreferences(context, getPassKeyStore(context), FILE_NAME_FLAG)
    }

    fun isConnected(): Boolean {
        return !TextUtils.isEmpty(getToken())
    }


    private fun setToken(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_FLAG, token)
        editor.commit()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_FLAG, null)
    }


    fun saveUser(user: User) {
        setToken(user.token)
        val jsonAdapter = moshi.adapter(User::class.java)
        val editor = sharedPreferences.edit()
        editor.putString(USER_FLAG, jsonAdapter.toJson(user))
        editor.apply()
    }

    fun getUser(): User {
        val json = sharedPreferences.getString(USER_FLAG, null)
        val adapter = moshi.adapter(User::class.java)
        return adapter.fromJson(json) as User
    }


    private fun getPassKeyStore(context: Context): String {
        val alias = context.applicationContext.packageName
        var pass:String?=null
        try {
            KeyStoreHelper.createKeys(context, alias)
            pass = KeyStoreHelper.getSigningKey(alias)
        } catch (e: Exception) {
            //Crashlytics.logException(e) TODO
        }
        if (pass == null) {
            pass = getDeviceSerialNumber(context)
            pass = bitShiftEntireString(pass)
        }
        return pass
    }
}