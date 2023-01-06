package com.amnpa.sbb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amnpa.sbb.viewmodel.*
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {
    val INTERNET_P = 101
    val ANS_P = 102
    val NOTIFICATIONS_P = 103
    val SCHEDULE_ALARM_P = 104

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<MeowBottomNavigation>(R.id.bottomNavigationView)

        bottomNavigationView.add(MeowBottomNavigation.Model(1, R.drawable.ic_user))
        bottomNavigationView.add(MeowBottomNavigation.Model(2, R.drawable.ic_games))
        bottomNavigationView.add(MeowBottomNavigation.Model(3, R.drawable.ic_league))

        bottomNavigationView.setOnShowListener {
            fun onShowItem() {
                goToFragment(it.id)
            }
        }

        bottomNavigationView.setOnClickMenuListener {
            goToFragment(it.id)
        }

        bottomNavigationView.setOnReselectListener {
            goToFragment(it.id)
        }

        bottomNavigationView.show(2, true)

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val id = prefs.getInt("user_id", -1)
        if (id == -1){
            val authActivity = Intent(this, AuthActivity::class.java)
            startActivity(authActivity)
        }
        else goToFragment(2)
    }

    private fun goToFragment(id: Int){
        val fragment = when(id){
            1 -> PlayerFragment()
            2 -> GamesFragment()
            else -> LeagueFragment()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}