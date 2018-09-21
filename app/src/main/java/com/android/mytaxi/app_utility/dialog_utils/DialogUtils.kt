package com.echannels.moismartservices.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.mytaxi.R


/**
 * Created by N!K$ on 7/30/18.
 */
class DialogUtils {

    companion object {

        /**
         * SINGLE BUTTON DIALOG: NO CALLBACK
         */
        fun showAlertDialogOnly(context: Context, message:String){
            val singleDialog = Dialog(context)
                singleDialog.setContentView(R.layout.single_button_dialog)
                singleDialog.setCanceledOnTouchOutside(false)
                singleDialog.setCancelable(true)
                singleDialog.window!!.attributes.windowAnimations = R.style.LoaderDiaogAnim
                singleDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
                (singleDialog.findViewById<Button>(R.id.buttonOk)).setOnClickListener{ singleDialog.dismiss() }
            (singleDialog.findViewById<TextView>(R.id.msgTV)).text = message
                singleDialog.show()
        }



        /**
         * SINGLE BUTTON DIALOG: WITH CALLBACK
         */

        fun showAlertDialogWithCallback(context: Context, clickListener: View.OnClickListener){
            val singleDialog = Dialog(context)
            singleDialog.setContentView(R.layout.single_button_dialog)
            singleDialog.setCanceledOnTouchOutside(false)
            singleDialog.setCancelable(true)
            singleDialog.window!!.attributes.windowAnimations = R.style.LoaderDiaogAnim
            singleDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
            (singleDialog.findViewById<Button>(R.id.buttonOk)).setOnClickListener{
                clickListener.onClick(it)
                singleDialog.dismiss()
            }
            singleDialog.show()
        }



        /**
         * DOUBLE BUTTON DIALOG or CONFIRMATION DIALOG: WITH CALLBACK
         */

        fun showConfirmationDialog(context: Context, displayMsg:String, clickListener: View.OnClickListener){
            val twoBtnDialog = Dialog(context)
            twoBtnDialog.setContentView(R.layout.two_button_dialog)
            twoBtnDialog.setCanceledOnTouchOutside(false)
            twoBtnDialog.setCancelable(true)
            twoBtnDialog.window!!.attributes.windowAnimations = R.style.LoaderDiaogAnim
            twoBtnDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
            twoBtnDialog.findViewById<TextView>(R.id.msgTV).text = displayMsg
            (twoBtnDialog.findViewById<Button>(R.id.btnOK)).setOnClickListener{
                twoBtnDialog.dismiss()
                clickListener.onClick(it)
            }
            (twoBtnDialog.findViewById<Button>(R.id.btnCancel)).setOnClickListener{
                twoBtnDialog.dismiss()
                clickListener.onClick(it)
            }
            twoBtnDialog.show()
        }

    }
}