package com.amnpa.sbb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.amnpa.sbb.viewmodel.GamesFragment
import com.amnpa.sbb.viewmodel.LeagueFragment
import com.amnpa.sbb.viewmodel.StatsFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<MeowBottomNavigation>(R.id.bottomNavigationView)

        bottomNavigationView.add(MeowBottomNavigation.Model(1, R.drawable.ic_user))
        bottomNavigationView.add(MeowBottomNavigation.Model(2, R.drawable.ic_games))
        bottomNavigationView.add(MeowBottomNavigation.Model(3, R.drawable.ic_league))

        goToFragment(GamesFragment())

        bottomNavigationView.setOnShowListener {
            fun onShowItem() {
                goToFragment(
                    when(it.id){
                        1 -> StatsFragment()
                        2 -> GamesFragment()
                        else -> LeagueFragment()
                    }
                )
            }
        }

        bottomNavigationView.setOnClickMenuListener{
            goToFragment(
                when(it.id){
                    1 -> StatsFragment()
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