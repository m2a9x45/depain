package com.example.depain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.train_row.view.*

class MainAdapter: RecyclerView.Adapter<CustomVeiwHolder>(){

    val testdata = listOf<String>("Aberdeen","Edinburgh","London","Glasgow","Dundee","Inverurie","Oxford","Aberdeen","Edinburgh","London","Glasgow","Dundee","Inverurie","Oxford")

    override fun getItemCount(): Int {
        return testdata.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVeiwHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.train_row, parent, false)
        return  CustomVeiwHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomVeiwHolder, position: Int) {
        val desTitle = testdata.get(position)
        holder?.itemView?.textView_des?.text = desTitle
    }
}

class CustomVeiwHolder(view: View): RecyclerView.ViewHolder(view) {

}