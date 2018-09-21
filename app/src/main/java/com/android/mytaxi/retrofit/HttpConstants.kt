package com.android.mytaxi.retrofit

/**
 * Created by N!K$ on 7/29/18.
 */
interface HttpConstants {
    companion object {

        //LATITUDE and LONGITUDE of Hamburg area to get all vehicles
        val sourceLat = 53.694865
        val sorceLong = 9.757589
        val destLat = 53.394655
        val destLong = 10.099891

        var BASE_URL = "https://fake-poi-api.mytaxi.com/"
    }
}