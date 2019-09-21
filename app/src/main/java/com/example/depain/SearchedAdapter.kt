package com.example.depain

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.train_row.view.*

class SearchedAdapter(val DepInfo: DepInfo): RecyclerView.Adapter<CustomVeiwHolder>(){


    val testdata = listOf<String>("Aberdeen","Edinburgh","London","Glasgow","Dundee","Inverurie","Oxford","Aberdeen","Edinburgh","London","Glasgow","Dundee","Inverurie","Oxford")

    override fun getItemCount(): Int {
        return DepInfo.departures.all.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVeiwHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.train_row, parent, false)
        return  CustomVeiwHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomVeiwHolder, position: Int) {
//        val desTitle = testdata.get(position)

        val desTitle = DepInfo.departures.all.get(position).destination_name
        val platform = DepInfo.departures.all.get(position).platform
        val aimedDepTime = DepInfo.departures.all.get(position).aimed_departure_time
        val operator_name = DepInfo.departures.all.get(position).operator_name
        val timetable = DepInfo.departures.all.get(position).service_timetable.id
        val operator = DepInfo.departures.all.get(position).operator

        holder?.itemView?.textView_des?.text = desTitle
        if (platform == "null") {platform}
        holder.itemView.textView_plat.text = "Platform: " + platform
        holder.itemView.textView_time.text = aimedDepTime
        holder.itemView.textView_opararor.text = operator_name

        holder.itemView.setOnClickListener { view ->
            val DesName = DepInfo.departures.all.get(position).destination_name

            println("User tapped the item : " + DesName)


            val intent = Intent(view.context, TrainDetailActivity::class.java)

            intent.putExtra("actionbarTitle", desTitle)
            intent.putExtra("aimedDepTime", aimedDepTime)
            intent.putExtra("platform", platform)
            intent.putExtra("operator_name", operator_name)
            intent.putExtra("service_timetable", timetable)
            intent.putExtra("operator", operator)

            view.context.startActivity(intent)

        }



    }
}

class CustomVeiwHolder(view: View): RecyclerView.ViewHolder(view) {

}