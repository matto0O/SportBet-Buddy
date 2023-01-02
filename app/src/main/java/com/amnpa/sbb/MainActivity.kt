package com.amnpa.sbb

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.amnpa.sbb.model.NotificationService
import com.amnpa.sbb.viewmodel.GamesFragment
import com.amnpa.sbb.viewmodel.LeagueFragment
import com.amnpa.sbb.viewmodel.PlayerFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {
    val INTERNET_P = 101
    val ANS_P = 102
    val NOTIFICATIONS_P = 103
    val SCHEDULE_ALARM_P = 104

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ActivityResultContracts.RequestPermission

        val sp = getSharedPreferences("prefs", MODE_PRIVATE)
        val date = sp.getString("time", "18:00")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, date!!.dropLast(3).toInt())
        calendar.set(Calendar.MINUTE, date.substring(3).toInt())

        val notification = PendingIntent.getBroadcast(
            applicationContext,
            0,
            Intent(this, NotificationService::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            notification
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, notification
        )

        val bottomNavigationView = findViewById<MeowBottomNavigation>(R.id.bottomNavigationView)

        bottomNavigationView.add(MeowBottomNavigation.Model(1, R.drawable.ic_user))
        bottomNavigationView.add(MeowBottomNavigation.Model(2, R.drawable.ic_games))
        bottomNavigationView.add(MeowBottomNavigation.Model(3, R.drawable.ic_league))

        goToFragment(GamesFragment())

        bottomNavigationView.setOnShowListener {
            fun onShowItem() {
                goToFragment(
                    when(it.id){
                        1 -> PlayerFragment()
                        2 -> GamesFragment()
                        else -> LeagueFragment()
                    }
                )
            }
        }

        bottomNavigationView.setOnClickMenuListener{
            goToFragment(
                when(it.id){
                    1 -> PlayerFragment()
                    2 -> GamesFragment()
                    else -> LeagueFragment()
                }
            )
        }

        bottomNavigationView.show(2, true)
    }

    private fun goToFragment(fragment: Fragment){

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}