package com.unifit.unifit

import android.Manifest
import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.unifit.unifit.domain.utils.AlarmReceiver
import com.unifit.unifit.domain.utils.RemindersManager
import com.unifit.unifit.presentation.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RemindersManager.startReminder(this, 16, 27)
        //setAlarm(this)

    }

    fun setAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Intent to be broadcasted when the alarm triggers
        val alarmIntent = Intent(context, AlarmReceiver::class.java)

        // Create a PendingIntent for the alarm
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1, // Use a unique request code
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to trigger at the desired time
        val alarmTimeMillis = System.currentTimeMillis() + 2000 // Calculate the time for the alarm
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, // Use RTC_WAKEUP for alarms that should wake up the device
            alarmTimeMillis,
            pendingIntent
        )
    }
}