package com.ab.appconfigrequirement.module.constant

import android.Manifest
import android.net.wifi.WifiManager
import android.provider.Settings

object PermissionConst {
    const val READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val WIFI_PERMISSION = Manifest.permission.ACCESS_WIFI_STATE
    const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    const val BACKGROUND_LOCATION_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    const val BATTERY_OPTIMIZATION = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
    const val BACKGROUND_SERVICE_LOCATION_ALWAYS_GRANTED = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    const val ACTIVITY_RECOGNITION = Manifest.permission.ACTIVITY_RECOGNITION

}

object ConnectionConst {
    const val LOCATION_INTENT = Settings.ACTION_LOCATION_SOURCE_SETTINGS
    const val BLUETOOTH_INTENT = Settings.ACTION_BLUETOOTH_SETTINGS
    const val WIFI_INTENT = WifiManager.ACTION_PICK_WIFI_NETWORK
}

object RequestCodeConst {
    const val REQUEST_PERMISSION = 35489

}