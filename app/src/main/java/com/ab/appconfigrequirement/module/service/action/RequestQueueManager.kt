package com.ab.appconfigrequirement.module.service.action

import androidx.lifecycle.MutableLiveData
import com.ab.appconfigrequirement.data.Action
import com.ab.appconfigrequirement.data.ActionType
import com.ab.appconfigrequirement.module.utils.minusAssign
import com.ab.appconfigrequirement.module.utils.plusAssign
import timber.log.Timber

/**
 * The role of this class is to manage permission, connection, and file request to user.
 * This way, we are allow to ask only one thing at a time
 */

object RequestQueueManager {

    /*
     * MutableLiveData of Action list
     */
    val permissionList: MutableLiveData<MutableList<String>> = MutableLiveData()
    val connectionList: MutableLiveData<MutableList<String>> = MutableLiveData()

    /*
     * Common queue
     */
    val actionList: MutableLiveData<MutableList<Action>> = MutableLiveData()

    init {
        actionList.value = mutableListOf()
        permissionList.value = mutableListOf()
        connectionList.value = mutableListOf()
    }

    /**
     * If a missing condition is detect by one of manager (permission/connection/file), an action will be
     * add to actionListLiveData (main handler) and to the list corresponding to action.type
     */
    fun addActionToQueue(action: Action) {
        if (actionList.value!!.count { it.request == action.request } == 0) {
            actionList += action
        }

        when (action.type) {
            ActionType.PERMISSION -> {
                if (!permissionList.value!!.contains(action.request)) {
                    permissionList += action.request
                    Timber.d("${action.request} is now add to permissionsQueue")
                }
            }
            ActionType.CONNECTION -> {
                if (!connectionList.value!!.contains(action.request)) {
                    connectionList += action.request
                    Timber.d("${action.request} is now add to connectionQueue")
                }
            }
        }
    }

    /**
     * When managers detect ex missing condition is restored to its normal state, action is remove from
     * actionListLiveData and to the list corresponding to action.type
     */
    fun removeActionFromQueue(action: Action) {
        if (actionList.value!!.contains(action)) {
            actionList -= action
        }
        when {
            permissionList.value!!.contains(action.request) -> {
                permissionList -= action.request
                Timber.d("${action.request} is now remove from permissionsQueue")
            }
            connectionList.value!!.contains(action.request) -> {
                connectionList -= action.request
                Timber.d("${action.request} is now remove from connectionQueue")
            }
        }
    }
}