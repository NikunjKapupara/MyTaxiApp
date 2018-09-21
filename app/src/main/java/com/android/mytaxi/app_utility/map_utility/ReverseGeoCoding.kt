package com.android.mytaxi.app_utility.map_utility

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import android.util.Log
import com.android.mytaxi.callbacks.OnSuccessfullyAddressFetched
import com.android.mytaxi.ws_models.GetAllVehiclesModel
import java.util.ArrayList
import java.util.Locale

/**
    Passing parameters to Async Task:
    //  Context : context for use
    //  OnSuccessfullyAddressFetched: Callback interface with address when operation done is done!
    //  Any: input parameters in::-> doInBackground
    //  Void: ,
    //  GetAllVehiclesModel: with return at the end of all service execution in:::-> onPostExecute
**/


class ReverseGeoCoding(private val mContext: Context,
                       private val onSuccessfullyAddressFetched: OnSuccessfullyAddressFetched) :
        AsyncTask<Any, Void, GetAllVehiclesModel>() {

    override fun doInBackground(vararg params: Any): GetAllVehiclesModel {
        val getAllVehiclesModel = params[0] as GetAllVehiclesModel
        for (i in 0 until getAllVehiclesModel.poiList.size) {
                getAllVehiclesModel.poiList[i].coordinate.addressFromLatLong = MapUtils.getCompleteAddressString(
                        mContext,
                        getAllVehiclesModel.poiList[i].coordinate.latitude,
                        getAllVehiclesModel.poiList[i].coordinate.longitude)
        }
        return getAllVehiclesModel
    }

    override fun onPostExecute(getAllVehiclesModel: GetAllVehiclesModel) {
        onSuccessfullyAddressFetched.getCallbackOnAddressFetched(getAllVehiclesModel)
    }
}
