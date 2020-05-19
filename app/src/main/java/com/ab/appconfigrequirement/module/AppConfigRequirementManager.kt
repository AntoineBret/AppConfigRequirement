package com.ab.appconfigrequirement.module

import com.ab.appconfigrequirement.module.service.connection.ConnectionsManager
import com.ab.appconfigrequirement.module.state.StateManager
import com.ab.appconfigrequirement.module.worker.WorkerManager

object AppConfigRequirementManager {

    private lateinit var appConfigRequirement: AppConfigRequirement

    internal fun init(appConfigRequirement: AppConfigRequirement) {

        this.appConfigRequirement = appConfigRequirement

        StateManager.initStateObservables()
        ConnectionsManager.initConnectionsListeners()
        WorkerManager.startRefreshPermissionPeriodicWorker()
    }

    internal fun getAppConfigRequirement(): AppConfigRequirement {
        return appConfigRequirement
    }
}

