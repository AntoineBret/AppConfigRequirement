package com.ab.appconfigrequirement.module.service.connection

import android.content.Context
import com.ab.appconfigrequirement.data.Connection
import com.ab.appconfigrequirement.module.AppConfigRequirementManager
import com.ab.appconfigrequirement.module.state.StateManager.connectionStateData
import com.ab.appconfigrequirement.module.state.ConnectionState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

object ConnectionsManager {

    private val disposable = CompositeDisposable()

    fun initConnectionsListeners() {

        val context = AppConfigRequirementManager.getAppConfigRequirement().context
        val connections = AppConfigRequirementManager.getAppConfigRequirement().connections

        Timber.d("Requested connections are $connections")
        connections?.forEach { connection ->
            when (connection) {
                Connection.INTERNET -> this.initInternetConnectionListener(context)
                Connection.WIFI -> this.initWifiConnectionListener(context)
                Connection.CELLULAR -> this.initCellularConnectionListener(context)
                Connection.BLUETOOTH -> this.initBluetoothConnectionListener(context)
                Connection.LOCATION -> this.initLocationConnectionListener(context)
            }
        }
    }

    private fun initInternetConnectionListener(context: Context) {
        disposable.add(
            ConnectionService.internetConnectivityIsEnabled(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { isInternetEnabled ->
                    connectionStateData.value = ConnectionState.InternetEnabled(isInternetEnabled)
                }
        )
    }

    private fun initWifiConnectionListener(context: Context) {

    }

    private fun initCellularConnectionListener(context: Context) {

    }

    private fun initBluetoothConnectionListener(context: Context) {
        disposable.add(
            ConnectionService.bluetoothConnectivityIsEnabled(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { isBluetoothEnabled ->
                    connectionStateData.value = ConnectionState.BluetoothEnabled(isBluetoothEnabled)
                }
        )
    }

    private fun initLocationConnectionListener(context: Context) {
        disposable.add(
            ConnectionService.locationConnectivityIsEnabled(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { isLocationEnabled ->
                    connectionStateData.value = ConnectionState.LocationEnabled(isLocationEnabled)
                }
        )
    }
}
