package com.android.mytaxi.ws_models

import java.io.Serializable

data class GetAllVehiclesModel(
        var poiList: List<Poi>
):Serializable {
    data class Poi(
            var id: Int, // 620360
            var coordinate: Coordinate,
            var fleetType: String, // TAXI
            var heading: Double // 145.10370896720082
    ):Serializable {
        data class Coordinate(
                var latitude: Double, // 53.42521821975765
                var longitude: Double, // 9.847054477011573
                //// user defined added data variable for showing address wherever we need to show
                var addressFromLatLong:String
        ):Serializable
    }
}