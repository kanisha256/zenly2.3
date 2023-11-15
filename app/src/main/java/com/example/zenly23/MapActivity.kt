package com.example.zenly23

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider

class MapActivity : Activity(), UserLocationObjectListener {
    private val TARGET_LOCATION: Point = Point(55.7887, 49.1221)
    private var mapView: MapView? = null
    private var userLocationLayer: UserLocationLayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.map)
        super.onCreate(savedInstanceState)
        mapView = findViewById<View>(R.id.mapview) as MapView
        mapView.getMap().move(
            CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 3),
            null
        )
        mapView.getMap().setRotateGesturesEnabled(true)
        // mapView.getMap().move(new CameraPosition(new Point(0, 0), 14, 0, 0));
        requestLocationPermission()
        val mapKit: MapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow())
        userLocationLayer.setVisible(true)
        userLocationLayer.setHeadingEnabled(true)
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_FINE_LOCATION"
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf("android.permission.ACCESS_FINE_LOCATION"),
                PERMISSIONS_REQUEST_FINE_LOCATION
            )
        }
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer.setAnchor(
            PointF((mapView.getWidth() * 0.5) as Float, (mapView.getHeight() * 0.5) as Float),
            PointF((mapView.getWidth() * 0.5) as Float, (mapView.getHeight() * 0.83) as Float)
        )
        userLocationView.getArrow().setIcon(
            ImageProvider.fromResource(
                this, R.drawable.user_arrow
            )
        )
        val pinIcon: CompositeIcon = userLocationView.getPin().useCompositeIcon()
        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(this, R.drawable.search_result),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE and -0x66000001)
    }

    fun onObjectRemoved(view: UserLocationView?) {}
    fun onObjectUpdated(view: UserLocationView?, event: ObjectEvent?) {}

    companion object {
        private const val PERMISSIONS_REQUEST_FINE_LOCATION = 1
    }
}