package com.unifit.unifit.presentation.ui.customs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import com.unifit.unifit.R

class AlertDialogEnterTimeAlarm {
    companion object{
        fun create(context: Context, saveTimeAlarm: (Int,Int) -> Unit): AlertDialog{
            val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_enter_time_alarm, null)

            val builder = AlertDialog.Builder(context)

            initializeDialogBuilder(builder, dialogView)

            val dialog = builder.create()

            initializeDialogView(dialogView, dialog) { saveWeight, saveHeight ->
                saveTimeAlarm(saveWeight, saveHeight)
            }

            return dialog
        }

        private fun initializeDialogBuilder(builder: AlertDialog.Builder, dialogView: View){
            builder.setView(dialogView)
        }

        private fun initializeDialogView(dialogView: View, dialog: AlertDialog, saveTimeAlarm: (Int, Int) -> Unit) {
            val npHours = dialogView.findViewById<NumberPicker>(R.id.hoursPicker)
            val npMinutes = dialogView.findViewById<NumberPicker>(R.id.minutesPicker)

            npHours.minValue = 0
            npHours.maxValue = 23
            npMinutes.minValue = 0
            npMinutes.maxValue = 59

            val btnSave = dialogView.findViewById<Button>(R.id.btnSave)
            btnSave.setOnClickListener {
                saveTimeAlarm(
                    npHours.value,
                    npMinutes.value
                )
                dialog.dismiss()
            }
        }
    }
}