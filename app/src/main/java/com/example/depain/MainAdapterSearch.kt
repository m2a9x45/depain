package com.example.depain

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_row.view.*
import kotlinx.android.synthetic.main.train_row.view.*
import okhttp3.*
import java.io.IOException


class MainAdapterSearch(val allStations: MainActivity.listOfStations): RecyclerView.Adapter<CustomVeiwHolder>(){

    override fun getItemCount(): Int {
        return allStations.test.count()
        println(allStations)
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

        holder.itemView.setOnClickListener { view ->
            val DesCode = allStations.test.get(position).CRS_Code
            val DesName = allStations.test.get(position).Station_Name

            println("User tapped the item : " + DesCode)


            val intent = Intent(view.context, SearchedActivity::class.java)

            intent.putExtra("StationCode", DesCode)
            intent.putExtra("StationName", DesName)

            view.context.startActivity(intent)
        }
    }
}


