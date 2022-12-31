package com.amnpa.sbb.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.amnpa.sbb.MainActivity
import com.amnpa.sbb.R

class NotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    fun showNotification(){
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            Intent(context, MainActivity::class.java),
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Notif title")
            .setContentText("Notification")
            .setContentIntent(pendingIntent)
            .build()

        Log.v("tf", "tf")

        notificationManager.notify(1, notification)
    }

    companion object{
        const val CHANNEL_ID = "channel1"
    }
}