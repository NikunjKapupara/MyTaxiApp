package com.android.mytaxi.adapters

import android.content.Context
import android.location.Location
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.mytaxi.R
import com.android.mytaxi.app_utility.AnimUtils
import com.android.mytaxi.app_utility.map_utility.MapUtils
import com.android.mytaxi.app_utility.map_utility.ReverseGeoCoding
import com.android.mytaxi.callbacks.OnRecyclerViewItemClicked
import com.android.mytaxi.key_constants.KeyConstants
import com.android.mytaxi.ws_models.GetAllVehiclesModel


/**
 * Created by N!K$ on 7/30/18.
 */
class VehicleListAdapter(private val context: Context,
                         private val vehicleListData:List<GetAllVehiclesModel.Poi>,
                         private val clickItemCallback:OnRecyclerViewItemClicked):
        RecyclerView.Adapter<VehicleListViewHolder>(){

    var previousPosition = 0

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {

        holder.vehicleTypeIV.setImageResource(if (vehicleListData[position].fleetType == KeyConstants.VEHICALE_TYPE_POOLING)
            R.drawable.ic_car_pool else R.drawable.ic_taxi)

        holder.vehicleLocationTV.text = vehicleListData[position].coordinate.addressFromLatLong
        holder.vehicleTypeTV.text = vehicleListData[position].fleetType
        holder.listItemLL.setOnClickListener { clickItemCallback.getCallbackOnItemClick(vehicleListData[position]) }

        //// List Item Smooth Animation
        AnimUtils.animateRecyclerviewItem(holder, position > previousPosition)

        previousPosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {
        return VehicleListViewHolder(LayoutInflater.from(context).inflate(R.layout.vehicle_listitem, parent, false))
    }

    override fun getItemCount(): Int {
        return vehicleListData.size
    }

}