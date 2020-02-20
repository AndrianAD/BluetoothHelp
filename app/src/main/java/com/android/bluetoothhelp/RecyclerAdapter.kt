package com.android.todohelper.adapter

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.bluetoothhelp.R
import java.util.*


class RecyclerAdapter(var context: Context, var onRecycleClick: OnRecyclerClick) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    lateinit var devicecList: ArrayList<BluetoothDevice>


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_event, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name?.text = devicecList[position].name
        viewHolder.cardView!!.setOnClickListener {
            onRecycleClick.onRecyclerClickEvent(devicecList[position])
        }

    }

    override fun getItemCount() = devicecList.size

    fun setArrayList(arrayList: ArrayList<BluetoothDevice>) {
        devicecList = arrayList
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView? = null
        var cardView: CardView? = null

        init {
            // v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            name = v.findViewById(R.id.tv_name)
            cardView = v.findViewById(R.id.cardView)

        }
    }


}

interface OnRecyclerClick {

    fun onRecyclerClickEvent(bluetoothDevice: BluetoothDevice)


}


