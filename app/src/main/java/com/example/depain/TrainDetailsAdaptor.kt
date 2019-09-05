package com.example.depain

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_row.view.*
import kotlinx.android.synthetic.main.search_row.view.textView_stationName
import kotlinx.android.synthetic.main.stops_row.view.*

class TrainDetailsAdaptor(val Timetable: Timetabledepartures) : RecyclerView.Adapter<CustomVeiwHolder>(){
    override fun getItemCount(): Int {
        return Timetable.stops.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVeiwHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.stops_row, parent, false)
        return  CustomVeiwHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomVeiwHolder, position: Int) {
//        val desTitle = testdata.get(position)

        val stationName = Timetable.stops.get(position).station_name
        val platform = Timetable.stops.get(position).platform
        val status = Timetable.stops.get(position).status
        var aimedArrivaltime = Timetable.stops.get(position).aimed_arrival_time

        if (position == 0) {
            aimedArrivaltime = "Starts here"
        } else {
            aimedArrivaltime = "Due at : " + aimedArrivaltime
        }

        holder?.itemView?.textView_stationName?.text = stationName
        holder?.itemView?.textView_platform?.text = "Platform " + platform
        holder?.itemView?.textView_status?.text = status.toLowerCase()
        holder?.itemView?.textView_arrivalTime?.text = aimedArrivaltime

    }
}