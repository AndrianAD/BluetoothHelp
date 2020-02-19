package com.android.bluetoohelplb

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.widget.Toast

const val REQUEST_ENABLE_BT = 2804
const val REQUEST_DISCOVER_BT = 2588

class BluetoothHelp(var activity: Activity) {

    private var bluetoothDevices: ArrayList<BluetoothDevice> = arrayListOf()
    var mBlueAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    fun turnOn(): Boolean {
        return if (!mBlueAdapter.isEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(intent, REQUEST_ENABLE_BT)
            true
        } else {
            false
        }
    }

    fun turnOff(): Boolean {
        return if (mBlueAdapter.isEnabled) {
            mBlueAdapter.disable()
            true
        } else {
            false
        }


    }

    fun makeDiscoverable(): Boolean {
        return if (!mBlueAdapter.isDiscovering) {
            activity.startActivityForResult(
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE),
                REQUEST_DISCOVER_BT
            )
            true
        } else false
    }

    fun getPairedDevices(): ArrayList<DeviceInfo> {
        var listOfDevice: ArrayList<DeviceInfo> = arrayListOf()
        if (mBlueAdapter.isEnabled) {
            bluetoothDevices = ArrayList(mBlueAdapter.bondedDevices)
        }
        for (device in bluetoothDevices){
            listOfDevice.add(DeviceInfo(deviceName = device.name,deviceAddress = device.address))
        }
        return listOfDevice
    }

}


data class DeviceInfo(
    val deviceName: String? = null,
    val deviceAddress: String? = null
)


fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

