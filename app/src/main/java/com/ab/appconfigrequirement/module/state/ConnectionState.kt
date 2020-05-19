package com.ab.appconfigrequirement.module.state

/**
 * A sealed class allows you to represent constrained hierarchies in which an object can only be of
 * one of the given types. In this case we handle app connection state
 * https://kotlinlang.org/docs/reference/sealed-classes.html
 */

sealed class ConnectionState {
    data class BluetoothEnabled(val isBluetoothEnabled: Boolean) : ConnectionState()
    data class InternetEnabled(val isInternetEnabled: Boolean) : ConnectionState()
    data class LocationEnabled(val isLocationEnabled: Boolean) : ConnectionState()
}