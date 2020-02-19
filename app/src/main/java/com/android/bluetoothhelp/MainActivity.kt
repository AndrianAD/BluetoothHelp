package com.android.bluetoothhelp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.bluetoohelplb.BluetoothHelp
import com.android.bluetoohelplb.REQUEST_DISCOVER_BT
import com.android.bluetoohelplb.REQUEST_ENABLE_BT
import com.android.bluetoohelplb.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bluetoothHelp = BluetoothHelp(this)



        turnOff.setOnClickListener { bluetoothHelp.turnOff() }
        turnOn.setOnClickListener { bluetoothHelp.turnOn() }
        discoverable.setOnClickListener {
            bluetoothHelp.makeDiscoverable()
        }
        scanPaired.setOnClickListener {
            toast(bluetoothHelp.getPairedDevices().toString())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                toast("Bluetooth is on")
            } else {
                toast("could't on bluetooth")
            }
        }

        if (requestCode == REQUEST_DISCOVER_BT) {
            if (resultCode == Activity.RESULT_OK) {
                toast("discover enabled")
            } else {
                toast("could't enable  for discovering")
            }

        }
    }
}
