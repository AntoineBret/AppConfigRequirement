package com.ab.appconfigrequirement.module.worker

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.ab.appconfigrequirement.module.AppConfigRequirementManager
import com.ab.appconfigrequirement.module.service.permission.PermissionService
import com.ab.appconfigrequirement.module.service.permission.PermissionsManager
import io.reactivex.Single
import timber.log.Timber

class RefreshPermissionWorker(val context: Context, val params: WorkerParameters) :
    RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        Timber.d("RefreshPermissionWorker createWork")
        PermissionsManager.initPermissionsListeners()
        return Single.just(Result.success())
    }

    companion object {
        const val TAG = "RefreshPermissionWorker"
    }
}