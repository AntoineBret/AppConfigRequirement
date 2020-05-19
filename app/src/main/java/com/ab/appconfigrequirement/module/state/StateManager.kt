package com.ab.appconfigrequirement.module.state

import androidx.lifecycle.MutableLiveData
import com.ab.appconfigrequirement.data.ActionConfig
import com.ab.appconfigrequirement.data.RequirementState
import com.ab.appconfigrequirement.module.service.action.RequestQueueManager
import timber.log.Timber

object StateManager {

    val permissionStateData: MutableLiveData<PermissionState> = MutableLiveData()
    val connectionStateData: MutableLiveData<ConnectionState> = MutableLiveData()
    val requirementStateData: MutableLiveData<RequirementState> = MutableLiveData()


    fun initStateObservables() {
        initPermissionStateObservable()
        initConnectionStateObservable()
    }

    private fun initPermissionStateObservable() {
        permissionStateData.observeForever { permissionState ->
            when (permissionState) {
                is PermissionState.AccessCoarseLocationGranted -> {
                    requirementStateData.value?.ACCESS_COARSE_LOCATION = permissionState.isLocationGranted
                    if (!permissionState.isLocationGranted) {
                        Timber.d("Location permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.locationPermissionAction)
                    } else {
                        Timber.d("Location permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.locationPermissionAction)
                    }
                }
                is PermissionState.AccessBackgroundLocationGranted -> {
                    requirementStateData.value?.ACCESS_BACKGROUND_LOCATION = permissionState.isAccessBackgroundLocationGranted
                    if (!permissionState.isAccessBackgroundLocationGranted) {
                        Timber.d("Access background location permission is not granted")
                    } else {
                        Timber.d("Access background location permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.accessBackgroundLocationPermissionAction)
                    }
                }
                is PermissionState.AccessFineLocationGranted -> {
                    requirementStateData.value?.ACCESS_FINE_LOCATION = permissionState.isAccessFineLocationGranted
                    if (!permissionState.isAccessFineLocationGranted) {
                        Timber.d("Access fine location permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.accessFineLocationPermissionAction)
                    } else {
                        Timber.d("Access fine location permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.accessFineLocationPermissionAction)
                    }
                }
                is PermissionState.ReadOnExternalStorageGranted -> {
                    if (!permissionState.isReadOnExternalStorageGranted) {
                        Timber.d("Read On External Storage permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.readOnExternalStoragePermissionAction)
                    } else {
                        Timber.d("Read On External Storage permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.readOnExternalStoragePermissionAction)
                    }
                }
                is PermissionState.WriteOnExternalStorageGranted -> {
                    if (!permissionState.isWriteOnExternalStorageGranted) {
                        Timber.d("Write On External Storage permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.writeOnExternalStoragePermissionAction)
                    } else {
                        Timber.d("Write On External Storage permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.writeOnExternalStoragePermissionAction)
                    }
                }
                is PermissionState.AccessWifiStateGranted -> {
                    if (!permissionState.isAccessWifiStateGranted) {
                        Timber.d("Access to wifi state permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.accessWifiStatePermissionAction)
                    } else {
                        Timber.d("Access to wifi state permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.accessWifiStatePermissionAction)
                    }
                }
                is PermissionState.BatteryOptimizationGranted -> {
                    if (!permissionState.isBatteryOptimizationGranted) {
                        Timber.d("Battery optimization is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.batteryOptimizationPermissionAction)
                    } else {
                        Timber.d("Battery optimization permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.batteryOptimizationPermissionAction)
                    }
                }
                is PermissionState.BackgroundServiceGranted -> {
                    if (!permissionState.isBackgroundServiceGranted) {
                        Timber.d("Background service permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.backgroundServicePermissionAction)
                    } else {
                        Timber.d("Background service permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.backgroundServicePermissionAction)
                    }
                }
                is PermissionState.ActivityRecognitionGranted -> {
                    if (!permissionState.isActivityRecognitionGranted) {
                        Timber.d("Activity recognition permission is not granted")
                        RequestQueueManager.addActionToQueue(ActionConfig.activityRecognitionPermissionAction)
                    } else {
                        Timber.d("Activity recognition permission is granted")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.activityRecognitionPermissionAction)
                    }
                }
            }
        }
    }

    private fun initConnectionStateObservable() {
        connectionStateData.observeForever { connectionState ->
            when (connectionState) {
                is ConnectionState.LocationEnabled -> {
                    if (!connectionState.isLocationEnabled) {
                        Timber.d("Location connection is disabled")
                        RequestQueueManager.addActionToQueue(ActionConfig.locationConnectionAction)
                    } else {
                        Timber.d("Location connection is enabled")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.locationConnectionAction)
                    }
                }
                is ConnectionState.BluetoothEnabled -> {
                    if (!connectionState.isBluetoothEnabled) {
                        Timber.d("Bluetooth connection is disabled")
                        RequestQueueManager.addActionToQueue(ActionConfig.bluetoothConnectionAction)
                    } else {
                        Timber.d("Bluetooth connection is enabled")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.bluetoothConnectionAction)
                    }
                }
                is ConnectionState.InternetEnabled -> {
                    if (!connectionState.isInternetEnabled) {
                        Timber.d("Internet connection is disabled")
                        RequestQueueManager.addActionToQueue(ActionConfig.wifiConnectionAction)
                    } else {
                        Timber.d("Internet connection is enabled")
                        RequestQueueManager.removeActionFromQueue(ActionConfig.wifiConnectionAction)
                    }
                }
            }
        }
    }
}