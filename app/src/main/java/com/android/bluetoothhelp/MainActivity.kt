package com.android.bluetoothhelp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bluetoohelplb.*
import com.android.todohelper.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickEvent {
    var devices: ArrayList<DeviceInfo> = arrayListOf()
    lateinit var adapter: RecyclerAdapter
    private var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bluetoothHelp = BluetoothHelp(this)


        adapter = RecyclerAdapter(
            context = this
        )
        adapter.setArrayList(ArrayList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager


        turnOff.setOnClickListener { bluetoothHelp.turnOff() }

        turnOn.setOnClickListener { bluetoothHelp.turnOn() }

        discoverable.setOnClickListener {
            bluetoothHelp.makeDiscoverable()
        }

        scanPaired.setOnClickListener {
            adapter.setArrayList(bluetoothHelp.getPairedDevices())
            adapter.notifyDataSetChanged()
        }

        scanExtraDevices.setOnClickListener {
            devices.clear()
            bluetoothHelp.getExtraDevices(this)

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

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {

            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toast("permission_granted")
                // ------go to next
            } else {
                // Permission request was denied.
                toast("permission_denied")
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, requestCode)

            }
        }
    }

    override fun callBack(deviceInfo: DeviceInfo) {
        devices.add(deviceInfo)
        currentDevicesTV.text = devices.toString()
        toast("activity callBAck")
        adapter.setArrayList(devices)
        adapter.notifyDataSetChanged()
    }
}


