package com.example.workingwithstorage.common

import android.util.Log

private const val DEFAULT_LOG_TAG = "AppDebug"

fun logDebug(message: String, tag: String = DEFAULT_LOG_TAG) {
    Log.d(tag, message)
}

