package com.ab.appconfigrequirement.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.ab.appconfigrequirement.data.Action
import com.ab.appconfigrequirement.data.Connection
import com.ab.appconfigrequirement.data.Permission
import com.ab.appconfigrequirement.data.Requirement
import com.ab.appconfigrequirement.module.service.action.ActionManager
import com.ab.appconfigrequirement.module.service.action.RequestQueueManager
import java.util.concurrent.TimeUnit

class AppConfigRequirement constructor(
    internal val context: AppCompatActivity,
    internal val permissions: List<Permission>?,
    internal val connections: List<Connection>?,
    internal val workerPeriodicCheck: Long?,
    internal val workerPeriodicCheckUnit: TimeUnit?
) : Requirement {

    data class Builder(
        private var context: AppCompatActivity? = null,
        private var permissionsList: List<Permission>? = null,
        private var connectionsList: List<Connection>? = null,
        private var workerPeriodicCheck: Long? = null,
        private var workerPeriodicCheckUnit: TimeUnit? = null
    ) {

        fun withPermission(permissions: List<Permission>) =
            apply { this.permissionsList = permissions }

        fun withConnection(connections: List<Connection>) =
            apply { this.connectionsList = connections }

        /**
         * Minimum period length is 15 minutes (same as JobScheduler)
         */
        fun withPeriodicCheck(time: Long, unit: TimeUnit) =
            apply {
                this.workerPeriodicCheck = time
                this.workerPeriodicCheckUnit = unit
            }


        fun build(): AppConfigRequirement {
            val appConfigRequirement = AppConfigRequirement(
                context!!,
                permissionsList,
                connectionsList,
                workerPeriodicCheck,
                workerPeriodicCheckUnit
            )
            AppConfigRequirementManager.init(appConfigRequirement)
            return appConfigRequirement
        }
    }

    override fun getRequirementState(): MutableLiveData<MutableList<Action>> {
        return RequestQueueManager.actionList
    }

    override fun getConnectionRequirementState(): MutableLiveData<MutableList<String>> {
        return RequestQueueManager.connectionList
    }

    override fun getPermissionRequirementState(): MutableLiveData<MutableList<String>> {
        return RequestQueueManager.permissionList
    }

    override fun setStateReady(action: Action) {
        return ActionManager.requestAction(action)
    }
}