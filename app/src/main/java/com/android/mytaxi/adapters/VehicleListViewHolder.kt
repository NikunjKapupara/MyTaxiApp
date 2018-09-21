package com.android.mytaxi.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.vehicle_listitem.view.*


/**
 * Created by N!K$ on 7/30/18.
 */
class VehicleListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val vehicleTypeIV = view.vehicleTypeIV!!
    val vehicleTypeTV = view.vehicleTypeTV!!
    val vehicleLocationTV = view.vehicleLocationTV!!
    val listItemLL = view.listItemLL!!

}