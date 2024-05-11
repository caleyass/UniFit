package com.unifit.unifit.domain.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar

object RemindersManager {
    const val REMINDER_NOTIFICATION_REQUEST_CODE = 1
    fun startReminder(
        context: Context,
        hours:Int,
        minutes:Int,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE,
    ) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
        }
        Log.d("TAG", "startReminder: ${calendar.time} ${calendar.timeInMillis} ${System.currentTimeMillis()}")
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Intent to be broadcasted when the alarm triggers
        val alarmIntent = Intent(context, AlarmReceiver::class.java)

        // Create a PendingIntent for the alarm
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId, // Use a unique request code
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
            pendingIntent
        )
    }


    fun stopReminder(
        context: Context,
        reminderId: Int = REMINDER_NOTIFICATION_REQUEST_CODE,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                reminderId,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
        alarmManager.cancel(intent)
    }
}