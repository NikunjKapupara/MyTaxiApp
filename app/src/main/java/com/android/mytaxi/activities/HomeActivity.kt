package com.android.mytaxi.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.android.mytaxi.R
import com.android.mytaxi.activities.module_activities.TaxiListingActivity
import com.android.mytaxi.activities.module_activities.TaxisOnMapActivity
import com.android.mytaxi.app_utility.NetworkUtils
import com.android.mytaxi.app_utility.dialog_utils.LoaderDialog
import com.android.mytaxi.app_utility.map_utility.ReverseGeoCoding
import com.android.mytaxi.callbacks.OnSuccessfullyAddressFetched
import com.android.mytaxi.key_constants.KeyConstants
import com.android.mytaxi.retrofit.HttpConstants
import com.android.mytaxi.retrofit.RetrofitApiService
import com.android.mytaxi.ws_models.GetAllVehiclesModel
import com.echannels.moismartservices.utils.DialogUtils
import com.echannels.moismartservices.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import java.io.Serializable


class HomeActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var getAllVehiclesModel: GetAllVehiclesModel?=null


    /**
     * Create Instance of the Retrofit API
     */
    private val RetrofitAPI by lazy {
        RetrofitApiService.create()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /// initially hide view to show animation purpose
        taxiListLL.visibility = View.GONE
        taxtiOnMapLL.visibility = View.GONE

        addEventListeners()
        getAllVehiclesFromHamburgArea()
    }


    /**
     * CLICK EVENT FOR Layout:
     *      1. Taxi Listing
     *      2. Taxis On Map
     */
    private fun addEventListeners() {
        taxiListLL.setOnClickListener { openTaxiListingScreen() }
        taxtiOnMapLL.setOnClickListener { openTaxiOnMapScreen() }
        internetUnAvailableLL.setOnClickListener { cautionOfNetworkUnAvailable() }
    }


    /***
     * If user not connected to Internet then show "Internet Unavailable view"
     */
    private fun cautionOfNetworkUnAvailable() {
        Toast.makeText(this, getString(R.string.error_internet_unvailable_message_vehicle_dataload), Toast.LENGTH_LONG).show()
    }


    /**
     * Open TAXIs ON MAP Activity
     */
    private fun openTaxiOnMapScreen() {
        val intentTaxisOnMap = Intent(this, TaxisOnMapActivity::class.java)
        intentTaxisOnMap.putExtra(KeyConstants.VEHICLE_DATA, getAllVehiclesModel as Serializable)
        startActivity(intentTaxisOnMap)
    }


    /**
     * Open TAXIs LISTING Activity
     */
    private fun openTaxiListingScreen() {
        val intentTaxiListing = Intent(this, TaxiListingActivity::class.java)
        intentTaxiListing.putExtra(KeyConstants.VEHICLE_DATA, getAllVehiclesModel as Serializable)
        startActivity(intentTaxiListing)
    }



    /**
     * CALL API to GET ALL VEHICLE in HAMBURG area.
     */
    private fun getAllVehiclesFromHamburgArea() {

        //Checking internet connection is available in device or not
        if(!NetworkUtils.isInternetAvailable(this)){
            categoryMenuLL.visibility = View.GONE
            internetUnAvailableLL.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_right))
            internetUnAvailableLL.visibility = View.VISIBLE
            return
        }

        ////showing Activity Loader while getting vehicles data.
        LoaderDialog.getInstance().showLoader(this, getString(R.string.loader_message_getting_data))


        ///// call service API to get the vehicle data
        disposable = RetrofitAPI.getAllVehicles(HttpConstants.sourceLat,
                HttpConstants.sorceLong,
                HttpConstants.destLat,
                HttpConstants.destLong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            LoaderDialog.getInstance().hideLoader()
                            LogUtils.getInstance().logError("SUCCESS: ", "result found")
                            getAddressFromLatLong(result)
                        },
                        { error ->
                            LoaderDialog.getInstance().hideLoader()
                            DialogUtils.showAlertDialogOnly(this, getString(R.string.error_getting_data))
                        })
    }


    /**
     * GET FULL ADDRESS FROM LATITUDE & LONGITUDE
     * Because we are not receiving address data in web service response
     */
    private fun getAddressFromLatLong(resultData:GetAllVehiclesModel) {
        try {
            if (resultData.poiList.isNotEmpty()) {
                LoaderDialog.getInstance().showLoader(this, getString(R.string.loader_message_getting_locations))
                ReverseGeoCoding(this, object : OnSuccessfullyAddressFetched {
                    override fun getCallbackOnAddressFetched(obj: Any) {
                        getAllVehiclesModel = obj as GetAllVehiclesModel
                        LogUtils.getInstance().logError("DONE!!!")
                        LoaderDialog.getInstance().hideLoader()
                        applyAnimation()
                    }
                }).execute(resultData)
            } else
                DialogUtils.showAlertDialogOnly(this, getString(R.string.no_vehicle_data))
        }
        catch (e:Exception){
            e.printStackTrace()
            LoaderDialog.getInstance().hideLoader()
        }
    }



    /**
     * ANIMATION on CATEGORY MENU
     *      1. TAXI On Map
     *      2. TAXI Listing
     */
    private fun applyAnimation() {
        val h = Handler()
        h.postDelayed({
            taxiListLL.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_right))
            taxtiOnMapLL.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_left))
            taxiListLL.visibility = View.VISIBLE
            taxtiOnMapLL.visibility = View.VISIBLE
        }, 250)
    }



    /**
     * Clear Disposable Web Api call
     */
    /*override fun onPause() {
        super.onPause()
        try {
            disposable?.dispose()
            disposable = null
            LoaderDialog.getInstance().hideLoader()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }*/



    /**
     * BACK PRESSED HANDLE
     * Ask user to Exit from the application.
     */
    override fun onBackPressed() {
        DialogUtils.showConfirmationDialog(this, getString(R.string.exit_confirmation_msg),
                View.OnClickListener { p0 ->
                    if (p0!!.id == R.id.btnOK)
                    finish()
        })
    }
}
