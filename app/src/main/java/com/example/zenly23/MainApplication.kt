package com.example.zenly23

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MainApplication : Application() {
    private val MAPKIT_API_KEY = "ba1e4ee2-a331-4269-8e6b-085954773601"
    override fun onCreate() {
        super.onCreate()
        // Set the api key before calling initialize on MapKitFactory.
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }
}