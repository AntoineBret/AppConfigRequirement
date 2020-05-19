package com.ab.appconfigrequirement.module.state

/**
 * A sealed class allows you to represent constrained hierarchies in which an object can only be of
 * one of the given types. In this case we handle app permission state
 * https://kotlinlang.org/docs/reference/sealed-classes.html
 */

sealed class PermissionState {
    data class AccessCoarseLocationGranted(val isLocationGranted: Boolean) : PermissionState()
    data class AccessBackgroundLocationGranted(val isAccessBackgroundLocationGranted: Boolean): PermissionState()
    data class AccessFineLocationGranted(val isAccessFineLocationGranted: Boolean): PermissionState()
    data class ReadOnExternalStorageGranted(val isReadOnExternalStorageGranted: Boolean) : PermissionState()
    data class WriteOnExternalStorageGranted(val isWriteOnExternalStorageGranted: Boolean) : PermissionState()
    data class BatteryOptimizationGranted(val isBatteryOptimizationGranted: Boolean): PermissionState()
    data class BackgroundServiceGranted(val isBackgroundServiceGranted: Boolean): PermissionState()
    data class AccessWifiStateGranted(val isAccessWifiStateGranted: Boolean) : PermissionState()
    data class ActivityRecognitionGranted(val isActivityRecognitionGranted: Boolean) : PermissionState()
}