package com.ab.appconfigrequirement.module.service.action

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.ab.appconfigrequirement.data.Action
import com.ab.appconfigrequirement.module.service.ui.RequestPermissionsFragment
import com.ab.appconfigrequirement.module.service.ui.source
import io.reactivex.Observable

const val TAG = "RequestPermissions"

/**
 * This service allow you to ask a permission to user, when an "Action" logic (.actionLogic) is equals to APP_SETTINGS
 * When this service is requested, it create a fragment from the activity actually displayed (given by AppLifecycleService),
 * and displayed wanted request.
 */
class RequestPermissionService(activity: AppCompatActivity) {

    private lateinit var mRequestPermissionsFragment : RequestPermissionsFragment

    init {
        mRequestPermissionsFragment = getPermissionsFragment(activity)!!
    }

    private fun getPermissionsFragment(activity: AppCompatActivity): RequestPermissionsFragment? {
        var requestPermissionsFragment = findRxPermissionsFragment(activity)
        val isNewInstance = requestPermissionsFragment == null
        if (isNewInstance) {
            requestPermissionsFragment = RequestPermissionsFragment()
            val fragmentManager = activity.supportFragmentManager
            fragmentManager
                .beginTransaction()
                .add(requestPermissionsFragment, TAG)
                .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return requestPermissionsFragment
    }

    private fun findRxPermissionsFragment(activity: AppCompatActivity): RequestPermissionsFragment? {
        return activity.supportFragmentManager
            .findFragmentByTag(TAG) as? RequestPermissionsFragment
    }

    /**
     * Start request for the given action, and then informs you when the result was received by the onActivityResult
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionForAppPackage(action: Action): Observable<Boolean> {
        return source
            .doOnSubscribe { mRequestPermissionsFragment.onRequestPermissionForAppPackage(action) }
            .doOnComplete { source.onComplete() }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionForApp(action: Action): Observable<Boolean> {
        return source
            .doOnSubscribe { mRequestPermissionsFragment.onRequestPermissionInApp(action) }
            .doOnComplete { source.onComplete() }
    }
}