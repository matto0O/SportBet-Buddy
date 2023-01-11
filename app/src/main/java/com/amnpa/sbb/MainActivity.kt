package com.amnpa.sbb

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        checkPermission(
            Manifest.permission.INTERNET,
            INTERNET_P)
        checkPermission(
            Manifest.permission.ACCESS_NETWORK_STATE,
            ANS_P)

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

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == INTERNET_P) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Internet Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Internet Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == ANS_P) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Network Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Network Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}