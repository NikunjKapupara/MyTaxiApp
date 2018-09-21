package com.android.mytaxi.activities.module_activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.android.mytaxi.R
import com.android.mytaxi.key_constants.KeyConstants
import com.android.mytaxi.ws_models.GetAllVehiclesModel
import com.echannels.moismartservices.utils.DialogUtils
import com.echannels.moismartservices.utils.LogUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_vehicles_on_map.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.GoogleMap.CancelableCallback




class TaxisOnMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var getAllVehiclesModel: GetAllVehiclesModel
    private val builder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles_on_map)
        bottomVehicleInfoLL.visibility = View.GONE
        initActionBar()
        initGoogleMap()
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
     * INIT Google Map
     */
    private fun initGoogleMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    /**
     * When Google Map is Ready to use
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        bindVehicleDataOnMap()
    }



    /**
     * Get data from Home screen and show on map when google map is ready
     */
    private fun bindVehicleDataOnMap() {
        if (intent.extras.get(KeyConstants.VEHICLE_DATA) != null) {
            getAllVehiclesModel = intent.extras.getSerializable(KeyConstants.VEHICLE_DATA) as GetAllVehiclesModel

            if (getAllVehiclesModel.poiList.isNotEmpty()) {
                LogUtils.getInstance().logError("data Size: ", getAllVehiclesModel.poiList.size.toString())
                loadVehiclesOnMap()
            } else {
                DialogUtils.showAlertDialogOnly(this, getString(R.string.error_no_vehicles_found))
            }
        }
    }



    /**
     * LOAD VEHICLE DATA as MARKERS on Map
     */
    private fun loadVehiclesOnMap() {
        try {
            for (item in getAllVehiclesModel.poiList) {
                builder.include(LatLng(item.coordinate.latitude, item.coordinate.longitude))

                mMap.addMarker(MarkerOptions()
                        .title(item.coordinate.addressFromLatLong)
                        .position(LatLng(item.coordinate.latitude, item.coordinate.longitude))
                        .icon(BitmapDescriptorFactory.fromResource(if (item.fleetType == KeyConstants.VEHICALE_TYPE_POOLING)
                    R.drawable.ic_annotation_taxi_poolling
                else
                    R.drawable.ic_annotation_taxi)))
            }
            focusMapCameraToLocation()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }



    /**
     * When Annotations/Markers Loaded ---> then Move Map camera to location.
     */
    private fun focusMapCameraToLocation() {
        if (getAllVehiclesModel.poiList.size == 1){
            val cameraPosition = CameraPosition.builder()
                    .target(LatLng(getAllVehiclesModel.poiList[0].coordinate.latitude, getAllVehiclesModel.poiList[0].coordinate.longitude))
                    .zoom(14f)
                    .bearing(0f)
                    .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 2000, onMapCameraComplete)
        }
        else{
            ////// AUTO FOCUS/ZOOM based on All Annotations covered on Map
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, 200), onMapCameraComplete)
        }
    }



    /**
     *  on MAP CAMERA ANIMATION COMPLETE
     */
    private val onMapCameraComplete = object : CancelableCallback{
        override fun onFinish() {
            bottomVehicleInfoLL.startAnimation(AnimationUtils.loadAnimation(this@TaxisOnMapActivity, R.anim.slide_up))
            bottomVehicleInfoLL.visibility = View.VISIBLE
            LogUtils.getInstance().logError("CAMERA MOVING DONE...")
        }
        override fun onCancel() {
            LogUtils.getInstance().logError("CAMERA CANCEL...")
        }
    }
}
