package com.example.depain

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
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

        supportActionBar?.title = navbarTitle + " timetable"


        findViewById<TextView>(R.id.textView_deptime).text = "Leaving at " + aimedDepTime
        findViewById<TextView>(R.id.textView_opname).text = "run by " + operator_name

//        logoImage.visibility = View.VISIBLE

//        if (operator == "GS") {
//            logoImage.setImageResource(R.drawable.caledoniansleeper)
//        } else if (operator == "XC") {
//            logoImage.setImageResource(R.drawable.crosscountry)
//        }
//        else if (operator == "XC") {
//            logoImage.setImageResource(R.drawable.crosscountry)
//        }
//        else if (operator == "GW") {
//            logoImage.setImageResource(R.drawable.gwr)
//        }
//        else if (operator == "Sn" || operator == "TL" ||  operator == "GX" || operator == "CC") {
//            logoImage.setImageResource(R.drawable.thameslink)
//        }
//        else if (operator == "GN") {
//            logoImage.setImageResource(R.drawable.greatnorthern)
//        }
//        else if (operator == "EM") {
//            logoImage.setImageResource(R.drawable.eastmidlands)
//        }
//        else if (operator == "GR") {
//            logoImage.setImageResource(R.drawable.lner)
//        }
//        else if (operator == "LO") {
//            logoImage.setImageResource(R.drawable.londonoverground)
//        }
//        else if (operator == "SR") {
//            logoImage.setImageResource(R.drawable.scotrail)
//        }
//        else if (operator == "XR") {
////            logoImage.setImageResource(R.drawable.tflrail)
//        }
//        else if (operator == "TP") {
//            logoImage.setImageResource(R.drawable.transpennineexpress)
//        }
//        else if (operator == "NT") {
//            logoImage.setImageResource(R.drawable.northern)
//        } else {
//            logoImage.visibility = View.GONE
//        }





        fetchJSON(timetable)
        fetchOperatorImgUrl(operator,usinglocalhost)

    }

    fun fetchOperatorImgUrl(operator: String, usinglocalhost : Boolean){

        var url = ""

        if (usinglocalhost){
            url = "http://10.0.2.2:3000/operator/${operator}/true"
        } else {
            url = "http://de.prettyawful.net:3000/operator/${operator}/false"
        }

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val logoUrl = gson.fromJson(body, logoUrl::class.java)

                println(logoUrl.img_url)


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