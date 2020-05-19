package com.ab.appconfigrequirement.module.service.connection

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import io.reactivex.Observable
import io.reactivex.android.MainThreadDisposable

object ConnectionService {

    /**
     * INTERNET CONNECTIVITY
     */
    fun internetConnectivityIsEnabled(context: Context): Observable<Boolean> {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworks: MutableList<Network> = mutableListOf()

        return Observable.create { emitter ->
            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onLost(network: Network) {
                    super.onLost(network)
                    // Remove network from active network list
                    activeNetworks.removeAll { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }
                    val isNetworkConnected = activeNetworks.isNotEmpty()

                    emitter.onNext(isNetworkConnected)
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    // Add to list of active networks if not already in list
                    if (activeNetworks.none { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }) activeNetworks.add(
                        network
                    )
                    val isNetworkConnected = activeNetworks.isNotEmpty()
                    emitter.onNext(isNetworkConnected)
                }
            }

            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    /**
     * GPS CONNECTIVITY
     */
    fun locationConnectivityIsEnabled(context: Context): Observable<Boolean> {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocationProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val filter = IntentFilter()
        filter.addAction(LocationManager.MODE_CHANGED_ACTION)

        return Observable.create { emitter ->
            emitter.onNext(isLocationProviderEnabled) //first init

            val receiver: BroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    emitter.onNext(isLocationProviderEnabled) //on state change
                }
            }
            context.registerReceiver(receiver, filter)
            emitter.setDisposable(object : MainThreadDisposable() {
                override fun onDispose() {
                    context.unregisterReceiver(receiver)
                }
            })
        }
    }

    /**
     * BLUETOOTH CONNECTIVITY
     */
    fun bluetoothConnectivityIsEnabled(context: Context): Observable<Boolean> {
        val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)

        return Observable.create { emitter ->
            emitter.onNext(bluetoothAdapter.isEnabled) //first init

            val receiver: BroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    when (bluetoothAdapter.state) { //on state change
                        BluetoothAdapter.STATE_ON -> emitter.onNext(true)
                        BluetoothAdapter.STATE_OFF -> emitter.onNext(false)
                    }
                }
            }
            context.registerReceiver(receiver, filter)
            emitter.setDisposable(object : MainThreadDisposable() {
                override fun onDispose() {
                    context.unregisterReceiver(receiver)
                }
            })
        }
    }
}