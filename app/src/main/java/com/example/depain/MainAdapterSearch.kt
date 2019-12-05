package com.example.depain

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_row.view.*

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


        val stationName = allStations.test[position].Station_Name
        val stationCode = allStations.test[position].CRS_Code

        holder.itemView.textView_stationName?.text = stationName
        holder.itemView.textView_stationCode?.text = stationCode

        holder.itemView.setOnClickListener { view ->
            val desCode = allStations.test[position].CRS_Code
            val desName = allStations.test[position].Station_Name

            val intent = Intent(view.context, SearchedActivity::class.java)

            intent.putExtra("StationCode", desCode)
            intent.putExtra("StationName", desName)

            view.context.startActivity(intent)
        }
    }
}


