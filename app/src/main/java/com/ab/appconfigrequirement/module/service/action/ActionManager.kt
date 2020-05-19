package com.ab.appconfigrequirement.module.service.action

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ab.appconfigrequirement.data.Action
import com.ab.appconfigrequirement.data.ActionLogic
import com.ab.appconfigrequirement.module.AppConfigRequirementManager
import com.ab.appconfigrequirement.module.constant.PermissionConst
import com.ab.appconfigrequirement.module.service.permission.PermissionsManager
import timber.log.Timber


object ActionManager {

    private val context = AppConfigRequirementManager.getAppConfigRequirement().context
    private val requestPermissionService = RequestPermissionService(context)

    /**
     * Action dispatcher
     */
    fun requestAction(action: Action) {
        Timber.d("Request action for $action")
        when (action.actionLogic) {
            ActionLogic.MANIFEST_PERMISSION_REQUEST -> {
                processManifestPermissionRequest(action)
            }
            ActionLogic.PACKAGE_PERMISSION_REQUEST -> {
                processPackagePermissionRequest(action)
            }
            ActionLogic.COMMON_SETTINGS -> {
                processPhoneSettingsRequest(action)
            }
        }
    }

    /**
     * Request app manifest permission
     * For example if the app need ACCESS_COARSE_LOCATION" permission, request
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     * What will display a pop up directly in the application
     */
    @SuppressLint("CheckResult")
    private fun processManifestPermissionRequest(action: Action) {
        requestPermissionService.requestPermissionForApp(action)
            .take(1)
            .subscribe({
                refreshObservers(action)
            }, { throwable ->
                Timber.d("processManifestPermissionRequest error cause $throwable")
            })
    }

    /**
     * Request app package permission
     * For example if the app need ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" permission, request
     * with an intent for this package name and wait for activity result
     * What will display a pop up directly in the application
     */
    @SuppressLint("CheckResult")
    private fun processPackagePermissionRequest(action: Action) {
        requestPermissionService.requestPermissionForAppPackage(action)
            .take(1)
            .subscribe({
                refreshObservers(action)
            }, { throwable ->
                Timber.d("requestPermissionForAppPackage error cause $throwable")
            })
    }

    /**
     * Send the user to the phone settings corresponding to this action
     * For example, if we need to enable location, this function will send user to phone Location settings
     * No need activity result here, broadcast receiver listen in real time if a state change
     */
    private fun processPhoneSettingsRequest(action: Action) {
        val commonIntent = Intent(action.request)
        commonIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, commonIntent, null)
    }

    /**
     * As there is currently no API allowing us to be informed in real time of the status of the authorizations,
     * we will have to manually update the observers
     */
    private fun refreshObservers(action: Action) {
        Timber.d("refresh observers for $action")
        when (action.request) {
            PermissionConst.LOCATION_PERMISSION -> {
                PermissionsManager.initLocationPermissionListener(context)
            } //notify PermissionState(location)

            PermissionConst.BACKGROUND_LOCATION_PERMISSION -> {
                PermissionsManager.initAccessBackgroundLocationPermissionListener(context)
            } //notify PermissionState(background)

            PermissionConst.FINE_LOCATION_PERMISSION -> {
                PermissionsManager.initAccessFineLocationListener(context)
            } //notify PermissionState(access fine)

            PermissionConst.READ_PERMISSION -> {
                PermissionsManager.initReadOnExternalStoragePermissionListener(context)
            } //notify PermissionState(read)

            PermissionConst.WRITE_PERMISSION -> {
                PermissionsManager.initWriteOnExternalStoragePermissionListener(context)
            } //notify PermissionState(write)

            PermissionConst.BATTERY_OPTIMIZATION -> {
                PermissionsManager.initBatteryOptimizationPermissionListener(context)
            } //notify PermissionState(batteryOptimization)

            PermissionConst.BACKGROUND_SERVICE_LOCATION_ALWAYS_GRANTED -> {
                //TODO check this listener (unknow)
//                PermissionsManager.initAccessBackgroundLocationPermissionListener(context)
            } //notify PermissionState(background location service always granted)

            PermissionConst.ACTIVITY_RECOGNITION -> {
                PermissionsManager.initActivityRecognitionPermissionListener(context)
            } //notify PermissionState(batteryOptimization)
        }
    }
}