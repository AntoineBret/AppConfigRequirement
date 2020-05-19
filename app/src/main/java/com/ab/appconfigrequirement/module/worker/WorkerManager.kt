package com.ab.appconfigrequirement.module.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ab.appconfigrequirement.module.AppConfigRequirementManager
import java.util.concurrent.TimeUnit

object WorkerManager  {

    @SuppressLint("RestrictedApi")
    internal fun startRefreshPermissionPeriodicWorker() {

        val requirement =  AppConfigRequirementManager.getAppConfigRequirement()

        if (requirement.workerPeriodicCheck != null && requirement.workerPeriodicCheckUnit != null)
        createWorker(requirement.context, requirement.workerPeriodicCheck, requirement.workerPeriodicCheckUnit)
    }

    private fun createWorker(
        context: Context,
        workerPeriodicCheck: Long,
        workerPeriodicCheckUnit: TimeUnit
    ) {

        val refreshPermissionRequest = PeriodicWorkRequestBuilder<RefreshPermissionWorker>(
            workerPeriodicCheck,
            workerPeriodicCheckUnit
        )
            .build()

        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                RefreshPermissionWorker.TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                refreshPermissionRequest
            )
        }
    }
}