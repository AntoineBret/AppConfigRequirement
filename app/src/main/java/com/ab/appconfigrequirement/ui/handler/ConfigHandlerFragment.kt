package com.ab.appconfigrequirement.ui.handler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ab.appconfigrequirement.R
import com.ab.appconfigrequirement.data.Action
import com.ab.appconfigrequirement.data.Connection
import com.ab.appconfigrequirement.data.Permission
import com.ab.appconfigrequirement.module.AppConfigRequirement
import kotlinx.android.synthetic.main.fragment_config.*
import java.util.concurrent.TimeUnit

val actualRequest: MutableLiveData<Action> = MutableLiveData()

class ConfigFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(ConfigHandlerViewModel::class.java)

        getRequirementState()
        makeRequirement()
    }

    private fun getConfigRequirement(): AppConfigRequirement {
        return AppConfigRequirement.Builder(this.activity as AppCompatActivity)
            .withConnection(connectionsList)
            .withPermission(permissionsList)
            .withPeriodicCheck(5, TimeUnit.SECONDS)
            .build()
    }

    private fun getRequirementState() {
        getConfigRequirement()
            .getRequirementState()
            .observe(this.activity!!, Observer {
                displayRequirement(it)
            })
    }

    private fun displayRequirement(it: MutableList<Action>) {
        if (!it.isNullOrEmpty()) {
            actualRequest.value = it.last()
            textView4.text = actualRequest.value?.request
        } else {
            actualRequest.value = null
//            this.activity!!.finish()
        }
    }

    private fun makeRequirement() {
        buttonDwilenLocationPermission.setOnClickListener {
            if (actualRequest.value != null) {
                getConfigRequirement().setStateReady(actualRequest.value!!)
            }
        }
    }

    companion object {

        fun getNewInstance(): ConfigFragment {
            return ConfigFragment()
        }

    }

    /**
     * Wanted connections
     */
    val connectionsList = listOf(
        Connection.INTERNET,
        Connection.BLUETOOTH,
        Connection.LOCATION
    )

    /**
     * Wanted permissions
     */
    val permissionsList = listOf(
        Permission.BATTERY_OPTIMIZATION,
        Permission.LOCATION_PERMISSION,
        Permission.ACTIVITY_RECOGNITION,
        Permission.BACKGROUND_LOCATION_PERMISSION
    )
}