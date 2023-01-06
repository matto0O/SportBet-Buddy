package com.amnpa.sbb.viewmodel

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.amnpa.sbb.R
import com.amnpa.sbb.model.NotificationScheduler
import java.sql.Time

const val TIME_TAG = "time"

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val button = preferenceManager.findPreference<Preference>("noti_time")
        val occurring = preferenceManager.findPreference<ListPreference>("noti_occur")
        val switch = preferenceManager.findPreference<SwitchPreferenceCompat>("noti_res")

        button!!.isEnabled =
            (occurring!!.value.toString() in listOf("Everyday", "Every week")) and switch!!.isChecked

        val sharedPreferences =
            requireActivity().getSharedPreferences("prefs", AppCompatActivity.MODE_PRIVATE)
        val spEditor = sharedPreferences.edit()

        var time = sharedPreferences.getString(TIME_TAG, "18:00")
        button.summary = time

        button.setOnPreferenceClickListener {
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    time = Time(hourOfDay, minute, 0).toString().slice(0..4)
                    spEditor.apply{
                        putString(TIME_TAG, time)
                        apply()
                    }
                    button.summary = time
                    NotificationScheduler.cancelNotification(requireActivity().applicationContext)
                    when(occurring.value){
                        "Everyday" -> NotificationScheduler.
                        scheduleNotification(requireActivity().applicationContext,true)

                        "Every week" -> NotificationScheduler.
                        scheduleNotification(requireActivity().applicationContext,false)

                        else -> {}
                    }

                },
                time.toString().slice(0 until 2).toInt(),
                time.toString().slice(3 until 5).toInt(),
                true
            )
            timePickerDialog.setTitle(R.string.notif_schedule_time)
            timePickerDialog.show()

            return@setOnPreferenceClickListener true
        }

        occurring.setOnPreferenceChangeListener { _, newValue ->
            NotificationScheduler.cancelNotification(requireActivity().applicationContext)
            when(newValue){
                "Everyday" -> {
                    NotificationScheduler.scheduleNotification(
                        requireActivity().applicationContext, true
                    )
                    button.isEnabled = true
                }
                "Every week" -> {
                    NotificationScheduler.scheduleNotification(
                        requireActivity().applicationContext, false
                    )
                    button.isEnabled = true
                }
                else -> button.isEnabled = false
            }
            return@setOnPreferenceChangeListener true
        }

    }
}