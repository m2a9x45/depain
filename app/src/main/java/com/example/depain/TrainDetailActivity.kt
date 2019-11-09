package com.example.depain

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_traindetails.*
import okhttp3.*
import java.io.IOException

class TrainDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traindetails)
        timetable_recylerview.layoutManager = LinearLayoutManager(this)

        val mypreference = MyPreference(this)
        var usinglocalhost = mypreference.getLocalhost()

        val navbarTitle = intent.getStringExtra("actionbarTitle")
        val aimedDepTime = intent.getStringExtra("aimedDepTime")
        val platform = intent.getStringExtra("platform")
        val operator_name = intent.getStringExtra("operator_name")
        val timetable = intent.getStringExtra("service_timetable")
        val operator = intent.getStringExtra("operator")

        supportActionBar?.title = "$navbarTitle timetable"

        findViewById<TextView>(R.id.textView_deptime).text = "Leaving at $aimedDepTime"
        findViewById<TextView>(R.id.textView_opname).text = "run by $operator_name"

        fetchJSON(timetable)
        fetchOperatorImgUrl(operator)

    }

    fun fetchOperatorImgUrl(operator: String){

        var url = "http://railmate.net:3000/app/operator/${operator}/false"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val logoUrl = gson.fromJson(body, logoUrl::class.java)

                runOnUiThread {
                    var logoImage = findViewById<ImageView>(R.id.image_imageView)
                    Picasso.get().load(logoUrl.img_url).into(logoImage)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)
            }
        })
    }

    private fun fetchJSON(timetable: String) {

        val request = Request.Builder().url(timetable).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val Timetabledepartures = gson.fromJson(body, Timetabledepartures::class.java)

                runOnUiThread {
                    timetable_recylerview.adapter = TrainDetailsAdaptor(Timetabledepartures)
                    val loadingIcon = findViewById<ProgressBar>(R.id.indeterminateBar)
                    loadingIcon.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)
            }
        })

    }
}

class Timetabledepartures(val operator: String, val stops: List<Timetabledep>)

class Timetabledep(val platform : String, val aimed_arrival_time : String, val status : String, val station_name : String)

class logoUrl(val img_url: String)