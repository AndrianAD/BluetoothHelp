package com.android.bluetoohelplb

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

const val REQUEST_ENABLE_BT = 2804
const val REQUEST_DISCOVER_BT = 2588
const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 123

class BluetoothHelp(var activity: Activity) {
    var mBlueAdapter: BluetoothAdapter=BluetoothAdapter.getDefaultAdapter()
    init {
        checkPermission()
    }

    private var bluetoothDevices: ArrayList<BluetoothDevice> = arrayListOf()

    var listOfExtraDevices: ArrayList<DeviceInfo> = arrayListOf()
    private var onClickEvent: OnClickEvent? = null


    val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action!!) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device.name != null) {
                        onClickEvent!!.callBack(device)
                    }
                }
            }
        }
    }


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

    fun makeDiscoverable(ms: Int = 300): Boolean {
        return if (!mBlueAdapter.isDiscovering) {
            val discoverableIntent: Intent =
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                    putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, ms)
                }
            activity.startActivity(discoverableIntent)
            true
        } else false
    }

    fun getPairedDevices(): ArrayList<BluetoothDevice> {
        val listOfDevice: ArrayList<BluetoothDevice> = arrayListOf()
        if (mBlueAdapter.isEnabled) {
            val pairedDevices: Set<BluetoothDevice>? = mBlueAdapter.bondedDevices
            pairedDevices?.forEach { device ->
                listOfDevice.add(
                    device
                )
            }
        }
        return listOfDevice
    }


    fun getExtraDevices(callbacks: OnClickEvent) {
        onClickEvent = callbacks
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(receiver, filter)
        mBlueAdapter.startDiscovery()
    }


    private fun checkPermission() {

        if (activity.checkSelfPermissionCompat(Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            //toast("Permission is available")
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (activity.shouldShowRequestPermissionRationaleCompat(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            activity.requestPermissionsCompat(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
        } else {
            activity.requestPermissionsCompat(
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }


}

data class DeviceInfo(
    val deviceName: String? = null,
    val deviceAddress: String? = null
)


fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun Activity.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)

fun Activity.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun Activity.requestPermissionsCompat(
    permissionsArray: Array<String>,
    requestCode: Int
) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}


interface OnClickEvent {
    fun callBack(device: BluetoothDevice)
}


