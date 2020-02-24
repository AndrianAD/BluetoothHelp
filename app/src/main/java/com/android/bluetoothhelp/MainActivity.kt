package com.android.bluetoothhelp

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bluetoohelplb.*
import com.android.todohelper.adapter.OnRecyclerClick
import com.android.todohelper.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BluetoothEvents, OnRecyclerClick {
    var devices: ArrayList<BluetoothDevice> = arrayListOf()
    lateinit var adapter: RecyclerAdapter
    private var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var bluetoothHelp: BluetoothHelp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        adapter = RecyclerAdapter(
            context = this, onRecycleClick = this
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
            bluetoothHelp.getExtraDevices()

        }

        send.setOnClickListener {
            if (editText.text.toString().isNotEmpty())
                bluetoothHelp.bluetoothConnectionService.write(editText.text.toString().toByteArray())
        }

    }

    override fun onResume() {
        bluetoothHelp = BluetoothHelp(this, this)
        super.onResume()
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

    override fun onDestroy() {
        try {
            unregisterReceiver(bluetoothHelp.receiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        super.onDestroy()
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

    override fun getIncommingMessage(message: String) {
        toast(message)
    }

    override fun callBackExtraDevice(device: BluetoothDevice) {
        devices.add(device)
        toast("activity callBAck")
        adapter.setArrayList(devices)
        adapter.notifyDataSetChanged()
    }

    override fun onRecyclerClickEvent(bluetoothDevice: BluetoothDevice) {
        bluetoothHelp.bluetoothConnectionService.startClient(bluetoothDevice)
    }

}


