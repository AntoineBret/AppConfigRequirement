package com.ab.appconfigrequirement.data

import androidx.lifecycle.MutableLiveData

interface Requirement {

    //return both permission and connection state
    fun getRequirementState(): MutableLiveData<MutableList<Action>>

    //return connection state
    fun getConnectionRequirementState(): MutableLiveData<MutableList<String>>

    //return permission state
    fun getPermissionRequirementState(): MutableLiveData<MutableList<String>>

    //grant all requirement
    fun setStateReady(action: Action)
}