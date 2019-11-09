package com.example.depain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_row.view.textView_stationName
import kotlinx.android.synthetic.main.stops_row.view.*

class TrainDetailsAdaptor(private val Timetable: Timetabledepartures) : RecyclerView.Adapter<CustomVeiwHolder>(){
    override fun getItemCount(): Int {
        return Timetable.stops.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVeiwHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.stops_row, parent, false)
        return  CustomVeiwHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomVeiwHolder, position: Int) {

        val stationName = Timetable.stops[position].station_name
        var platform = Timetable.stops[position].platform
        var status = Timetable.stops[position].status
        var aimedArrivaltime = Timetable.stops[position].aimed_arrival_time

        aimedArrivaltime = if(position == 0) {
            "Starts here"
        } else {
            "Due at : $aimedArrivaltime"
        }

        if (status == null) {
            status = ""
        }

        holder.itemView.textView_stationName?.text = stationName
        holder.itemView.textView_platform?.text = "Platform $platform"
        holder.itemView.textView_status?.text = status.toLowerCase()
        holder.itemView.textView_arrivalTime?.text = aimedArrivaltime


    }
}