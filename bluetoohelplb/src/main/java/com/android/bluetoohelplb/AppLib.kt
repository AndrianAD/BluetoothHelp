package com.android.bluetoohelplb

import android.app.Application


open class AppLib : Application() {

    companion object {
        lateinit var instance: AppLib
        var bluetoothConnectionService: BluetoothConnectionService? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}

