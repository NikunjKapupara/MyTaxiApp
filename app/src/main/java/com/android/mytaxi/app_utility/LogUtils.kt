package com.echannels.moismartservices.utils

import android.util.Log

/**
 * Created by Nikunjkumar on 7/31/18.
 */
class LogUtils() {

    init {
        /**
        *  every time init is called increment instance count
        *  just in case somehow we break singleton rule, this will be
        *  called more than once and myInstancesCount > 1 == true
        */
        ++myInstancesCount
        Log.e("Instance Count; ", myInstancesCount.toString())
    }

    companion object {
        private val TAG = "MY_TAXI"
        //Debuggable field to check instance count
        var myInstancesCount = 0
        private val mInstance: LogUtils = LogUtils()

        @Synchronized
        fun getInstance(): LogUtils {
            return mInstance
        }
    }

    fun logDebug(log: String) {
        logDebug(TAG, log)
    }

    fun logError(log: String) {
        logError(TAG, log)
    }

    private fun logDebug(tag: String, log: String) {
        try {
            if (log.length > 3000) {
                logLargeString(log)
            } else {
                Log.d(tag, log)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LOG-DEBUG:", "LOG PRINTING ERROR OCCURED...!")
        }
    }

    private fun logLargeString(data: String) {
        val maxLogSize = 1000
        for (i in 0..data.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > data.length) data.length else end
            Log.v(TAG, data.substring(start, end))
        }
    }

    private fun logErrorLargeString(tag: String, data: String) {
        val maxLogSize = 4000
        for (i in 0..data.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > data.length) data.length else end


            Log.e(tag, data.substring(start, end))
        }
    }

    fun logError(tag: String, log: String?) {
        var tag = tag
        var log = log
        try {
            if (log == null) {
                return
            }
            if (tag.isEmpty())
                tag = " "
            else if (log.isEmpty())
                log = " "

            if (log.length > 3000) {
                logErrorLargeString(tag, log)
            } else {
                Log.e(tag, log)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LOG-ERROR:", "LOG PRINTING ERROR OCCURED...!")
        }
    }
}