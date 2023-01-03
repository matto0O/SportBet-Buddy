package com.amnpa.sbb.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity

object NotificationScheduler {

    private const val code = 1

    fun scheduleNotification(context: Context?, everyday: Boolean){
        val sp = context!!.getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        val date = sp.getString("time", "18:00")
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, date!!.dropLast(3).toInt())
        calendar.set(Calendar.MINUTE, date.substring(3).toInt())

        val notification = PendingIntent.getBroadcast(
            context,
            code,
            Intent(context, NotificationService::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * (if (everyday) 1 else 7),
            notification
        )
    }

    fun cancelNotification(context: Context?){
        val alarmManager =
            context!!.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val notification = PendingIntent.getBroadcast(
            context,
            code,
            Intent(context, NotificationService::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(notification)
    }
}