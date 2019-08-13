package com.example.depain

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_row.view.*
import kotlinx.android.synthetic.main.train_row.view.*

class MainAdapterSearch(val allStations: MainActivity.listOfStations): RecyclerView.Adapter<CustomVeiwHolder>(){

    override fun getItemCount(): Int {
        return allStations.test.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVeiwHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_row, parent, false)
        return  CustomVeiwHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomVeiwHolder, position: Int) {
//        val desTitle = testdata.get(position)

        val stationName = allStations.test.get(position).Station_Name
        val stationCode = allStations.test.get(position).CRS_Code

        holder?.itemView?.textView_stationName?.text = stationName
        holder?.itemView?.textView_stationCode?.text = stationCode

    }
}