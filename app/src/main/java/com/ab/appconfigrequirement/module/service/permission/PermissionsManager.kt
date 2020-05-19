package com.ab.appconfigrequirement.module.service.permission

import android.content.Context
import com.ab.appconfigrequirement.data.Permission
import com.ab.appconfigrequirement.module.AppConfigRequirementManager
import com.ab.appconfigrequirement.module.state.PermissionState
import com.ab.appconfigrequirement.module.state.StateManager.permissionStateData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

object PermissionsManager {

    private val disposable = CompositeDisposable()

    internal fun initPermissionsListeners() {

        val context = AppConfigRequirementManager.getAppConfigRequirement().context
        val permissions = AppConfigRequirementManager.getAppConfigRequirement().permissions

        Timber.d("Requested permissions are $permissions")
        permissions?.forEach { permission ->
            when (permission) {
                Permission.ES_READ_PERMISSION -> this.initReadOnExternalStoragePermissionListener(
                    context
                )
                Permission.ES_WRITE_PERMISSION -> this.initWriteOnExternalStoragePermissionListener(
                    context
                )
                Permission.LOCATION_PERMISSION -> this.initLocationPermissionListener(
                    context
                )
                Permission.BACKGROUND_LOCATION_PERMISSION -> this.initAccessBackgroundLocationPermissionListener(
                    context
                )
                Permission.FINE_LOCATION_PERMISSION -> this.initAccessFineLocationListener(
                    context
                )
                Permission.BATTERY_OPTIMIZATION -> this.initBatteryOptimizationPermissionListener(
                    context
                )
                Permission.ACTIVITY_RECOGNITION ->  this.initActivityRecognitionPermissionListener(
                    context
                )
            }
        }
    }

    /**
     * ACCESS BACKGROUND LOCATION
     */
    internal fun initAccessBackgroundLocationPermissionListener(context: Context) {
        disposable.add(
            PermissionService.accessBackgroundLocationPermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isBackgroundPermissionGranted ->
                    permissionStateData.value = PermissionState.AccessBackgroundLocationGranted(
                        isBackgroundPermissionGranted
                    )
                }, { throwable ->
                    Timber.d("accessBackgroundLocationPermissionIsGranted error = $throwable")
                })
        )
    }

    /**
     * BATTERY OPTIMIZATION
     */
    internal fun initBatteryOptimizationPermissionListener(context: Context) {
        disposable.add(
            PermissionService.batteryOptimizationPermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isBatteryOptimizationGranted ->
                    permissionStateData.value =
                        PermissionState.BatteryOptimizationGranted(isBatteryOptimizationGranted)
                }, { throwable ->
                    Timber.d("batteryOptimizationPermissionIsGranted error = $throwable")
                })
        )
    }

    /**
     * READ EXTERNAL STORAGE
     */
    internal fun initReadOnExternalStoragePermissionListener(context: Context) {
        disposable.add(
            PermissionService.readOnExternalStoragePermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isReadGranted ->
                    permissionStateData.value =
                        PermissionState.ReadOnExternalStorageGranted(isReadGranted)
                }, { throwable ->
                    Timber.d("readOnExternalStoragePermissionIsGranted error = $throwable")
                })
        )
    }

    /**
     * WRITE EXTERNAL STORAGE
     */
    internal fun initWriteOnExternalStoragePermissionListener(context: Context) {
        disposable.add(
            PermissionService.writeOnExternalStoragePermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isWriteGranted ->
                    permissionStateData.value =
                        PermissionState.WriteOnExternalStorageGranted(isWriteGranted)
                }, { throwable ->
                    Timber.d("writeOnExternalStoragePermissionIsGranted error = $throwable")
                })
        )
    }

    /**
     * ACCESS COARSE LOCATION
     */
    internal fun initLocationPermissionListener(context: Context) {
        disposable.add(
            PermissionService.accessCoarseLocationPermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isAccessCoarseLocationGranted ->
                    permissionStateData.value =
                        PermissionState.AccessCoarseLocationGranted(isAccessCoarseLocationGranted)
                }, { throwable ->
                    Timber.d("accessCoarseLocationPermissionIsGranted error = $throwable")
                })
        )
    }

    /**
     * ACCESS FINE LOCATION
     */
    internal fun initAccessFineLocationListener(context: Context) {
        disposable.add(
            PermissionService.accessFineLocationPermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isAccessFineGranted ->
                    permissionStateData.value =
                        PermissionState.AccessFineLocationGranted(isAccessFineGranted)
                }, { throwable ->
                    Timber.d("accessFineLocationPermissionIsGranted error = $throwable")
                })
        )
    }

    internal fun initActivityRecognitionPermissionListener(context: Context) {
        disposable.add(
            PermissionService.activityRecognitionPermissionIsGranted(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isActivityRecognitionGranted ->
                    permissionStateData.value =
                        PermissionState.ActivityRecognitionGranted(isActivityRecognitionGranted)
                }, { throwable ->
                    Timber.d("accessFineLocationPermissionIsGranted error = $throwable")
                })
        )
    }

}