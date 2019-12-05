package com.example.depain

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class SearchedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_searched)

        searched_recylerView.layoutManager = LinearLayoutManager(this)

        val mySwipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

        val StationCode = intent.getStringExtra("StationCode")
        val StationName = intent.getStringExtra("StationName")
        supportActionBar?.title = StationName + " Depatures"

        val mypreference = MyPreference(this)
        var usinglocalhost = mypreference.getLocalhost()

        if (StationCode != null){
                fetchJson(StationCode, usinglocalhost, mySwipeRefreshLayout)
        }

        fun myUpdateOperation() {
            fetchJson(StationCode, usinglocalhost, mySwipeRefreshLayout)

        }

        mySwipeRefreshLayout.setOnRefreshListener {
            println("onRefresh called from SwipeRefreshLayout")

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            myUpdateOperation()

        }
    }

    // Station string is station code
    private fun fetchJson(Station: String, localhost: Boolean, mySwipeRefreshLayout: SwipeRefreshLayout) {

        var url = "http://railmate.net:3060/app/livedepatures/${Station}"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                println(body)
                println("body got")

                val gson = GsonBuilder().create()

                val DepInfo = gson.fromJson(body, DepInfo::class.java)

                runOnUiThread {
                    searched_recylerView.adapter = SearchedAdapter(DepInfo)

                    val loadingSpinner = findViewById<ProgressBar>(R.id.indeterminateBar)
                    loadingSpinner.visibility = View.GONE
                    mySwipeRefreshLayout.isRefreshing = false
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)
            }
        })
    }
}



class DepInfo(val departures: departures)

class departures(val all: List<dep>)

class dep(val platform : String, val aimed_departure_time : String, val destination_name : String, val operator_name : String, val operator : String , val service_timetable :timetable)

class timetable(val id: String)