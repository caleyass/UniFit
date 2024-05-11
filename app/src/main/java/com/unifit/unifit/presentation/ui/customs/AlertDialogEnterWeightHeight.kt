package com.unifit.unifit.presentation.ui.customs

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.unifit.unifit.R
import java.lang.NumberFormatException

//TODO WRITE CLEANER CODE
class AlertDialogEnterWeightHeight {
    companion object {
        fun create(context: Context, saveWeightHeight: (Int, Int) -> Unit): AlertDialog {
            val sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_name_user_data), Context.MODE_PRIVATE)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_enter_weight_height, null)

            val builder = AlertDialog.Builder(context)

            initializeDialogBuilder(builder, dialogView)

            val dialog = builder.create()

            initializeDialogView(dialogView, dialog, sharedPreferences) { saveWeight, saveHeight ->
                saveWeightHeight(saveWeight, saveHeight)
            }

            return dialog
        }


        private fun initializeDialogView(dialogView: View, dialog: AlertDialog, sharedPreferences:SharedPreferences, saveWeightHeight: (Int, Int) -> Unit) {
            val etWeight = dialogView.findViewById<EditText>(R.id.etWeight)
            val etHeight = dialogView.findViewById<EditText>(R.id.etHeight)
            if(sharedPreferences.contains("weight")){
                etWeight.setText(sharedPreferences.getInt("weight", 0).toString())
            }
            if(sharedPreferences.contains("height")){
                etHeight.setText(sharedPreferences.getInt("height", 0).toString())
            }
            val btnSave = dialogView.findViewById<Button>(R.id.btnSave)
            btnSave.setOnClickListener {
                saveDialogView(etWeight, etHeight, sharedPreferences)
                {
                    saveWeightHeight(etWeight.text.toString().toInt(), etHeight.text.toString().toInt())
                    dialog.dismiss()
                }
            }
        }
         fun saveDialogView(
             etWeight: EditText,
             etHeight: EditText,
             sharedPreferences: SharedPreferences,
             closeDialog: () -> Unit
         ) {
            val etWeightValue =  getNumberFromEditText(etWeight)
            val etHeightValue = getNumberFromEditText(etHeight)

            if (etWeightValue > 0 && etHeightValue > 0) {
                sharedPreferences.edit().putInt("weight", etWeightValue).apply()
                sharedPreferences.edit().putInt("height", etHeightValue).apply()
                closeDialog()
            }

        }

        private fun getNumberFromEditText(et: EditText): Int {
            return try {
                val number = et.text.toString().toInt()
                if( number <= 0){
                    throw NumberFormatException()
                } else {
                    number
                }
            } catch (e: NumberFormatException) {
                et.setError("Please enter a valid number")
                0
            }
        }

        private fun initializeDialogBuilder(builder: AlertDialog.Builder, dialogView: View){
            builder.setView(dialogView)
        }
    }
}