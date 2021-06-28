package com.budianto.mytourismapp.utils

import android.app.AlertDialog
import android.content.Context

fun setDataPrefString(context: Context, key: String, value: String) {
    val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(key, value).apply()
}

fun getDataPrefBoolean(context: Context, key: String): Boolean{
    val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, false)
}


fun alertDialogDefaultTitle(context: Context?, title: String, message: String?) {
    val success = AlertDialog.Builder(context!!)
    success.setTitle(title)
    success.setMessage(message)
    success.setPositiveButton("Ok") { _, _ ->

    }
    success.setCancelable(false)
    success.create().show()
}

fun alertDialogBiasa(context: Context?, message: String?) {
    val success = AlertDialog.Builder(context!!)
    success.setMessage(message)
    success.setPositiveButton("Ok") { _, _ ->

    }
    success.setCancelable(false)
    success.create().show()
}