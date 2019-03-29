package com.mvvm.global.utils

/**
 * Created on 2/2/18.
 */
object DebugLog {

    private val E = 0
    private val W = 1
    private val D = 2
    private val I = 3
    private val V = 4

    /**
     * Logs MESSAGES
     *
     * @param flag
     * @param tag
     * @param msg
     */
    private fun Log(flag: Int, tag: String, msg: String) {
        //if (!BuildConfig.DEBUG)
        //    return;

        when (flag) {
            E -> android.util.Log.e(tag, msg)
            W -> android.util.Log.w(tag, msg)
            D -> android.util.Log.d(tag, msg)
            I -> android.util.Log.i(tag, msg)
            V -> android.util.Log.v(tag, msg)
            else -> {
            }
        }
    }

    /**
     * Logs MESSAGES with throwable
     *
     * @param flag
     * @param tag
     * @param msg
     * @param throwbale
     */
    private fun Log(flag: Int, tag: String, msg: String, throwbale: Throwable) {
        //if (!BuildConfig.DEBUG)
        //    return;

        when (flag) {
            E -> android.util.Log.e(tag, msg, throwbale)
            W -> android.util.Log.w(tag, msg, throwbale)
            D -> android.util.Log.d(tag, msg, throwbale)
            I -> android.util.Log.i(tag, msg, throwbale)
            V -> android.util.Log.v(tag, msg, throwbale)
            else -> {
            }
        }
    }

    fun e(tag: String, msg: String) {
        Log(E, tag, msg)
    }

    fun e(tag: String, msg: String, throwable: Throwable) {
        Log(E, tag, msg, throwable)
    }

    fun w(tag: String, msg: String) {
        Log(W, tag, msg)
    }

    fun w(tag: String, msg: String, throwable: Throwable) {
        Log(W, tag, msg, throwable)
    }

    fun d(tag: String, msg: String) {
        Log(D, tag, msg)
    }

    fun d(tag: String, msg: String, throwable: Throwable) {
        Log(D, tag, msg, throwable)
    }

    fun i(tag: String, msg: String) {
        Log(I, tag, msg)
    }

    fun i(tag: String, msg: String, throwable: Throwable) {
        Log(I, tag, msg, throwable)
    }

    fun v(tag: String, msg: String) {
        Log(V, tag, msg)
    }

    fun v(tag: String, msg: String, throwable: Throwable) {
        Log(V, tag, msg, throwable)
    }

}
