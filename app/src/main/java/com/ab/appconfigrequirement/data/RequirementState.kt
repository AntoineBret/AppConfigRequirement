package com.ab.appconfigrequirement.data

data class RequirementState(
    internal var READ_EXTERNAL_STORAGE: Boolean? = null,
    internal var WRITE_EXTERNAL_STORAGE: Boolean? = null,
    internal var ACCESS_WIFI_STATE: Boolean? = null,
    internal var ACCESS_COARSE_LOCATION: Boolean? = null,
    internal var ACCESS_BACKGROUND_LOCATION: Boolean? = null,
    internal var ACCESS_FINE_LOCATION: Boolean? = null,
    internal var ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS: Boolean? = null,
    internal var ACTION_APPLICATION_DETAILS_SETTINGS: Boolean? = null,
    internal var ACTIVITY_RECOGNITION: Boolean? = null,
    internal var ACTION_LOCATION_SOURCE_SETTINGS: Boolean? = null,
    internal var ACTION_BLUETOOTH_SETTINGS: Boolean? = null,
    internal var ACTION_PICK_WIFI_NETWORK: Boolean? = null
    )