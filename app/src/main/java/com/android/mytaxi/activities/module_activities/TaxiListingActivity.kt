package com.android.mytaxi.activities.module_activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import com.android.mytaxi.R
import com.android.mytaxi.adapters.VehicleListAdapter
import com.android.mytaxi.callbacks.OnRecyclerViewItemClicked
import com.android.mytaxi.key_constants.KeyConstants
import com.android.mytaxi.ws_models.GetAllVehiclesModel
import com.echannels.moismartservices.utils.DialogUtils
import com.echannels.moismartservices.utils.LogUtils
import kotlinx.android.synthetic.main.activity_taxi_listing.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import java.io.Serializable

class TaxiListingActivity : AppCompatActivity() {

    private lateinit var getAllVehiclesModel: GetAllVehiclesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi_listing)

        initActionBar()
        bindVehicleDataInList()
    }



    /**
     * INIT ACTIONBAR (TOOLBAR)
     */
    private fun initActionBar() {
        backBTN.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_right))
        backBTN.visibility = View.VISIBLE
        backBTN.setOnClickListener { finish() }
        toolbarTitleTV.text = getString(R.string.screen_title_taxi)
    }



    /**
     * Bind Vehicles data in Recycler View
     */
    private fun bindVehicleDataInList() {
        try {
            if (intent.extras.get("vehicle_data") != null) {
                getAllVehiclesModel = intent.extras.getSerializable(KeyConstants.VEHICLE_DATA) as GetAllVehiclesModel

                if (getAllVehiclesModel.poiList.isNotEmpty()) {
                    LogUtils.getInstance().logError("List data Size: ", getAllVehiclesModel.poiList.size.toString())

                    val linearLayoutManager = LinearLayoutManager(this)
                    vehicleListRV.layoutManager = linearLayoutManager
                    vehicleListRV!!.adapter = VehicleListAdapter(this, getAllVehiclesModel.poiList, object : OnRecyclerViewItemClicked {
                        override fun getCallbackOnItemClick(obj: Any) {

                            val listItemData = obj as GetAllVehiclesModel.Poi
                            val eachVehicleObjectModel = GetAllVehiclesModel(listOf(listItemData))

                            val intentTaxiListing = Intent(this@TaxiListingActivity, TaxisOnMapActivity::class.java)
                            intentTaxiListing.putExtra(KeyConstants.VEHICLE_DATA, eachVehicleObjectModel as Serializable)
                            startActivity(intentTaxiListing)
                        }
                    })
                } else {
                    DialogUtils.showAlertDialogOnly(this, getString(R.string.error_no_vehicles_found))
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}
