package com.amnpa.sbb.model

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.amnpa.sbb.MainActivity
import com.amnpa.sbb.R

object NotificationService: BroadcastReceiver(){
    const val CHANNEL_ID = "channel1"

    override fun onReceive(context: Context?, intent: Intent){
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
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

}