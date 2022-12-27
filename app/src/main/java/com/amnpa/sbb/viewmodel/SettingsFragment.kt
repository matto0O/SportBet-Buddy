package com.amnpa.sbb.viewmodel

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.amnpa.sbb.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}