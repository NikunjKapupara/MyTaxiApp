package com.android.mytaxi.app_utility.map_utility

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.android.mytaxi.adapters.VehicleListViewHolder
import com.android.mytaxi.key_constants.KeyConstants
import com.echannels.moismartservices.utils.LogUtils
import java.util.*

class MapUtils {

    companion object {

        /**
         * GETTING FULL ADDRESS FROM LATITUDE and LONGITUDE
         */
         fun getCompleteAddressString(context: Context, LATITUDE: Double, LONGITUDE: Double): String {
            var strAdd = ""
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
                if (addresses != null) {
                    val returnedAddress = addresses[0]
                    val strReturnedAddress = StringBuilder("")

                    for (i in 0..returnedAddress.maxAddressLineIndex) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                    //LogUtils.getInstance().logError("loction address", strReturnedAddress.toString())
                } else {
                    strAdd = KeyConstants.NO_ADDRESS_FOUND
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.w("loction address", "Canont get Address!")
                strAdd = KeyConstants.NO_ADDRESS_FOUND
            }
            return strAdd
        }
    }
}
