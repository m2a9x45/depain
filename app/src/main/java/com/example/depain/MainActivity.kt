package com.example.depain

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import android.view.View
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    var stationList = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.main_editText)
        val linkaccountbutton = findViewById<Button>(R.id.main_linkbutton)


        searched_recylerView.layoutManager = LinearLayoutManager(this)
        getAllStation()

        linkaccountbutton.setOnClickListener {
            val intent = Intent(this, LinkActivity::class.java)
            this.startActivity(intent)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            var matchedStations: MutableList<String> = ArrayList()
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val gson = GsonBuilder().create()
                val listOfStations = gson.fromJson(stationList, listOfStations::class.java)
                if (listOfStations != null) {
                    if (s.length >= 4) {
                        for ((i, item) in listOfStations.test.withIndex()) {
                            if (listOfStations.test[i].Station_Name.contains(s,ignoreCase = true)) {
                                matchedStations.add("{\"Station_Name\":\"${listOfStations.test[i].Station_Name}\",\"CRS_Code\":\"${listOfStations.test[i].CRS_Code}\"}")
                            }
                        }
                        val matchedStationsString = "{\"test\":${matchedStations}}"
                        val gson2 = GsonBuilder().create()
                        val matchedlistOfStations = gson2.fromJson(matchedStationsString, MainActivity.listOfStations::class.java)
                        runOnUiThread {
                            searched_recylerView.adapter = MainAdapterSearch(matchedlistOfStations)
                        }
                        matchedStations = ArrayList()
                    } else {
                        val listOfStations = gson.fromJson(stationList, listOfStations::class.java)
                        runOnUiThread {
                            searched_recylerView.adapter = MainAdapterSearch(listOfStations)
                        }
                    }
                }
            }
        })
    }

    fun getAllStation(){

        var url = "http://railmate.net:3060/app/stations"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                stationList = body.toString()
                println("Responce got")

                val gson = GsonBuilder().create()

                val listOfStations = gson.fromJson(body, listOfStations::class.java)

                runOnUiThread {
                    searched_recylerView.adapter = MainAdapterSearch(listOfStations)
                    val errorText = findViewById<TextView>(R.id.main_errorText)
                    val loadingIcon = findViewById<ProgressBar>(R.id.indeterminateBar)
                    errorText.visibility = View.GONE
                    loadingIcon.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)

                runOnUiThread {
                    val errorText = findViewById<TextView>(R.id.main_errorText)
                    val loadingIcon = findViewById<ProgressBar>(R.id.indeterminateBar)
                    errorText.visibility = View.VISIBLE
                    loadingIcon.visibility = View.GONE
                }
            }
        })
    }

    class listOfStations(val test: List<Name>)
    class Name(val Station_Name: String, val CRS_Code: String)
}


