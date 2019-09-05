package com.example.depain

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_traindetails.*
import okhttp3.*
import java.io.IOException

class TrainDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_traindetails)

        timetable_recylerview.layoutManager = LinearLayoutManager(this)


        val navbarTitle = intent.getStringExtra("actionbarTitle")
        val aimedDepTime = intent.getStringExtra("aimedDepTime")
        val platform = intent.getStringExtra("platform")
        val operator_name = intent.getStringExtra("operator_name")
        val timetable = intent.getStringExtra("service_timetable")

        supportActionBar?.title = navbarTitle + " timetable"


        findViewById<TextView>(R.id.textView_deptime).text = "Leaving at " + aimedDepTime
        findViewById<TextView>(R.id.textView_opname).text = "run by " + operator_name

        fetchJSON(timetable)

    }


    fun fetchJSON(timetable: String) {

        val url = timetable

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val Timetabledepartures = gson.fromJson(body, Timetabledepartures::class.java)

                runOnUiThread {
                    timetable_recylerview.adapter = TrainDetailsAdaptor(Timetabledepartures)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)
            }
        })

    }
}

class Timetabledepartures(val stops: List<Timetabledep>)

class Timetabledep(val platform : String, val aimed_arrival_time : String, val status : String, val station_name : String)