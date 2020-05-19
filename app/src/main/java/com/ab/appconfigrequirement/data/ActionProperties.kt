package com.ab.appconfigrequirement.data

import com.ab.appconfigrequirement.module.constant.ConnectionConst
import com.ab.appconfigrequirement.module.constant.PermissionConst

data class Action(
    //Request type - for example, can be a Manifest.permission or Settings intent)
    val request: String,
    //Action type - can be a connection or a permission
    val type: ActionType,
    //Request logic - ask request with an intent or a request (made with RxPermissions library)
    val actionLogic: ActionLogic
)

enum class ActionLogic {
    //Means we have to send the user to the app settings corresponding to this action (for example Settings.LOCATION_INTENT (common to all app)
    COMMON_SETTINGS,

    //Means that we can manage this action by a request with Intent(request) for packageName (for example BATTERY_OPTIMIZATION logic)
    PACKAGE_PERMISSION_REQUEST,

    //Means that we can manage this action by a request with requestPermissions(arrayOf(Manifest.permission.request)
    MANIFEST_PERMISSION_REQUEST
}

enum class ActionType {
    CONNECTION,
    PERMISSION
}

object ActionConfig {

    /**
     * PERMISSION
     */

    val locationPermissionAction = Action(
        PermissionConst.LOCATION_PERMISSION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val accessBackgroundLocationPermissionAction = Action(
        PermissionConst.BACKGROUND_LOCATION_PERMISSION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val accessFineLocationPermissionAction = Action(
        PermissionConst.FINE_LOCATION_PERMISSION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val readOnExternalStoragePermissionAction = Action(
        PermissionConst.READ_PERMISSION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val writeOnExternalStoragePermissionAction = Action(
        PermissionConst.WRITE_PERMISSION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val accessWifiStatePermissionAction = Action(
        PermissionConst.WIFI_PERMISSION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val batteryOptimizationPermissionAction = Action(
        PermissionConst.BATTERY_OPTIMIZATION,
        ActionType.PERMISSION,
        ActionLogic.PACKAGE_PERMISSION_REQUEST
    )

    val backgroundServicePermissionAction = Action(
        PermissionConst.BACKGROUND_SERVICE_LOCATION_ALWAYS_GRANTED,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )

    val activityRecognitionPermissionAction = Action(
        PermissionConst.ACTIVITY_RECOGNITION,
        ActionType.PERMISSION,
        ActionLogic.MANIFEST_PERMISSION_REQUEST
    )


    /**
     * CONNECTION
     */

    val locationConnectionAction = Action(
        ConnectionConst.LOCATION_INTENT,
        ActionType.CONNECTION,
        ActionLogic.COMMON_SETTINGS
    )

    val bluetoothConnectionAction = Action(
        ConnectionConst.BLUETOOTH_INTENT,
        ActionType.CONNECTION,
        ActionLogic.COMMON_SETTINGS
    )

    val wifiConnectionAction = Action(
        ConnectionConst.WIFI_INTENT,
        ActionType.CONNECTION,
        ActionLogic.COMMON_SETTINGS
    )
}