package com.ab.appconfigrequirement.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ab.appconfigrequirement.BuildConfig
import com.ab.appconfigrequirement.R
import com.ab.appconfigrequirement.ui.handler.ConfigFragment
import com.ab.appconfigrequirement.utils.replaceFragmentWithoutBackStack
import timber.log.Timber


class ConfigRequirementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmentWithoutBackStack(ConfigFragment.getNewInstance(), R.id.container_framelayout)


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }



    }
}
