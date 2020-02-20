package com.android.todohelper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.bluetoohelplb.DeviceInfo
import com.android.bluetoothhelp.R

import java.util.*


class RecyclerAdapter(var context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    lateinit var devicecList: ArrayList<DeviceInfo>


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_event, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name?.text = devicecList[position].deviceName
        viewHolder.cardView!!.setOnClickListener {


        }

    }

    override fun getItemCount() = devicecList.size

    fun setArrayList(arrayList: ArrayList<DeviceInfo>) {
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


