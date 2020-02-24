package com.android.bluetoothhelp

import com.android.bluetoohelplb.AppLib


class App : AppLib() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this


    }


}

