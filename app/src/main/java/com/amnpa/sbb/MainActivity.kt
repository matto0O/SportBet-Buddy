package com.amnpa.sbb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.amnpa.sbb.viewmodel.AuthFragment
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

        val bottomNavigationView = findViewById<MeowBottomNavigation>(R.id.bottomNavigationView)

        bottomNavigationView.add(MeowBottomNavigation.Model(1, R.drawable.ic_user))
        bottomNavigationView.add(MeowBottomNavigation.Model(2, R.drawable.ic_games))
        bottomNavigationView.add(MeowBottomNavigation.Model(3, R.drawable.ic_league))
        bottomNavigationView.add(MeowBottomNavigation.Model(4, R.drawable.ic_league))

        goToFragment(GamesFragment())

        bottomNavigationView.setOnShowListener {
            fun onShowItem() {
                goToFragment(it)
            }
        }

        bottomNavigationView.setOnClickMenuListener{
            goToFragment(it)
        }

        bottomNavigationView.setOnReselectListener {
            goToFragment(it)
        }

        bottomNavigationView.show(2, true)
    }

    private fun goToFragment(it: MeowBottomNavigation.Model){
        val fragment = when(it.id){
            1 -> PlayerFragment()
            2 -> GamesFragment()
            3 -> LeagueFragment()
            else -> AuthFragment()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    private fun goToFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}