package com.example.zenly23

import android.os.Bundle
import android.preference.PreferenceActivity
import com.yandex.mapkit.MapKitFactory

class MainActivity : PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.main)
        MapKitFactory.initialize(this)
    }
}