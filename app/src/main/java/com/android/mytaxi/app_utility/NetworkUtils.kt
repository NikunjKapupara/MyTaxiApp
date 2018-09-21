package com.android.mytaxi.app_utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.echannels.moismartservices.utils.LogUtils

class NetworkUtils {

    companion object {

        fun isInternetAvailable(context: Context): Boolean {
            try {
                val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                if (connectivity != null) {
                    val info = connectivity.allNetworkInfo
                    if (info != null) {
                        for (i in info.indices) {
                            LogUtils.getInstance().logError("INTERNET:", i.toString())
                            if (info[i].state == NetworkInfo.State.CONNECTED) {
                                LogUtils.getInstance().logError("INTERNET:", "connected!")
                                return true
                            }
                        }
                    }
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }
}