package com.android.mytaxi.app_utility.dialog_utils

import android.app.Activity
import android.app.Dialog
import android.widget.TextView
import com.android.mytaxi.R


/**
 * LOADER DIALOG
 */
class LoaderDialog {

    private var dialogLoader: Dialog? = null

    ///////////////////////
    // INITIALIZATION
    companion object {

        private var mInstance: LoaderDialog = LoaderDialog()

        @Synchronized
        fun getInstance(): LoaderDialog {
            return mInstance
        }
    }
    ///////////////////////END-Initialization


    fun showLoader(activity: Activity, loaderMessage: String){

        dialogLoader = Dialog(activity)
        dialogLoader!!.setContentView(R.layout.loader_dialog_layout)
        dialogLoader!!.setCancelable(false)
        dialogLoader!!.setCanceledOnTouchOutside(false)
        dialogLoader!!.window.attributes.windowAnimations = R.style.LoaderDiaogAnim
        dialogLoader!!.window.setBackgroundDrawableResource(android.R.color.transparent)
        if (!loaderMessage.isEmpty())
            (dialogLoader!!.findViewById<TextView>(R.id.loaderMessageTV)).text = loaderMessage
        dialogLoader!!.show()
    }

    fun hideLoader(){
        if (dialogLoader != null && dialogLoader!!.isShowing) {
            dialogLoader!!.hide()
            dialogLoader!!.dismiss()
            dialogLoader = null
        }
    }
}