package com.ab.appconfigrequirement.module.service.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ab.appconfigrequirement.data.Action
import com.ab.appconfigrequirement.module.constant.RequestCodeConst
import io.reactivex.subjects.PublishSubject
import timber.log.Timber


val source: PublishSubject<Boolean> = PublishSubject.create()

/**
 * A fragment allowing us to request permissions and receive responses from it.
 */
class RequestPermissionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        Timber.d("RequestPermissionsFragment created")
    }

    //APP PACKAGE REQUEST
    fun onRequestPermissionForAppPackage(action: Action) {
        val appIntent = Intent(action.request)
        appIntent.data = Uri.parse("package:" + this.activity!!.packageName)
        startActivityForResult(appIntent, RequestCodeConst.REQUEST_PERMISSION)
        Timber.d("onRequestPermissionForAppPackage requested ${action.request}, wait for result")
    }

    //APP PACKAGE RESULT
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RequestCodeConst.REQUEST_PERMISSION -> source.onNext(true)
            else -> source.onNext(false)
        }
    }

    //APP REQUEST
    fun onRequestPermissionInApp(action: Action) {
        this.requestPermissions(
            arrayOf(action.request),
            RequestCodeConst.REQUEST_PERMISSION
        )
        Timber.d("onRequestPermissionInApp requested ${action.request}, wait for result")
    }

    //APP RESULT
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            RequestCodeConst.REQUEST_PERMISSION -> source.onNext(true)
            else -> source.onNext(false)
        }
    }
}