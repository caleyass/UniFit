package com.unifit.unifit.domain.utils

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.unifit.unifit.R

class AlarmReceiver : BroadcastReceiver() {

    /**
     * sends notification when receives alarm
     * and then reschedule the reminder again
     * */
    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)
        sendReminderNotification(
            applicationContext = context,
            channelId = context.getString(R.string.reminders_notification_channel_id)
        )

    }
}

fun sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {
    with(NotificationManagerCompat.from(applicationContext)) {
        var builder = NotificationCompat.Builder(applicationContext, "1")
            .setSmallIcon(R.drawable.fit_girl)
            .setContentTitle("My notification")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // notificationId is a unique int for each notification that you must define.
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notify(1, builder.build())

    }
}

private fun createNotificationChannel(applicationContext: Context) {
    val name = "channel_name"
    val descriptionText = "channel_description"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(applicationContext.getString(R.string.reminders_notification_channel_id), name, importance).apply {
        description = descriptionText
    }
    // Register the channel with the system.
    val notificationManager: NotificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    notificationManager.createNotificationChannel(channel)

}



const val NOTIFICATION_ID = 1