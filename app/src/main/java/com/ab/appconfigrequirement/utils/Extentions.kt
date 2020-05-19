package com.ab.appconfigrequirement.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction()
        .func()
        .addToBackStack(null)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commitAllowingStateLoss()
}

inline fun FragmentManager.inTransactionWithoutBackStack(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction()
        .func()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun AppCompatActivity.replaceFragmentWithoutBackStack(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransactionWithoutBackStack { replace(frameId, fragment) }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    fragmentManager!!.inTransaction { replace(frameId, fragment) }
}

fun Fragment.addFragment(fragment: Fragment, frameId: Int) {
    fragmentManager!!.inTransaction { add(frameId, fragment) }
}