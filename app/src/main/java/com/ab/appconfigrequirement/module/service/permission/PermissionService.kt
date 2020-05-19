package com.ab.appconfigrequirement.module.service.permission

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import androidx.core.content.ContextCompat
import io.reactivex.Observable

object PermissionService {

    /**
     * ACCESS COARSE LOCATION
     */
    fun accessCoarseLocationPermissionIsGranted(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            val locationPermissionResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            emitter.onNext(locationPermissionResult == PackageManager.PERMISSION_GRANTED)
        }
    }

    /**
     * ACCESS BACKGROUND LOCATION
     */
    @TargetApi(Build.VERSION_CODES.Q)
    fun accessBackgroundLocationPermissionIsGranted(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            val backgroundLocationPermissionResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
            emitter.onNext(backgroundLocationPermissionResult == PackageManager.PERMISSION_GRANTED)
        }
    }

    /**
     * ACCESS FINE LOCATION
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun accessFineLocationPermissionIsGranted(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            val accessFineLocationPermissionResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            emitter.onNext(accessFineLocationPermissionResult == PackageManager.PERMISSION_GRANTED)
        }
    }

    /**
     * BATTERY OPTIMIZATION
     */
    fun batteryOptimizationPermissionIsGranted(context: Context): Observable<Boolean> {
        val powerManager = (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
        return Observable.create { emitter ->
            val batteryOptimizationPermissionResult =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    powerManager.isIgnoringBatteryOptimizations(context.packageName)
                } else {
                    true
                }
            emitter.onNext(batteryOptimizationPermissionResult)
        }
    }

    /**
     * READ EXTERNAL STORAGE
     */
    fun readOnExternalStoragePermissionIsGranted(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            val readOnExternalStoragePermissionResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
            emitter.onNext(readOnExternalStoragePermissionResult == PackageManager.PERMISSION_GRANTED)
        }
    }

    /**
     * WRITE EXTERNAL STORAGE
     */
    fun writeOnExternalStoragePermissionIsGranted(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            val writeOnExternalStoragePermissionResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            emitter.onNext(writeOnExternalStoragePermissionResult == PackageManager.PERMISSION_GRANTED)
        }
    }

    /**
     * ACTIVITY RECOGNITION
     */
    fun activityRecognitionPermissionIsGranted(context: Context): Observable<Boolean> {
        return Observable.create { emitter ->
            val activityRecognitionPermissionResult =
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)
            emitter.onNext(activityRecognitionPermissionResult == PackageManager.PERMISSION_GRANTED)
        }
    }
}