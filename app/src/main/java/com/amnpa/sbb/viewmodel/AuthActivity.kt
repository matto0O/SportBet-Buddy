package com.amnpa.sbb.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amnpa.sbb.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContentView(R.layout.activity_auth)
    }

    override fun onBackPressed() {}
}