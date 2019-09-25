package com.example.depain

import android.content.Context
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
import android.content.SharedPreferences
import android.view.View
import android.widget.Switch

class MainActivity : AppCompatActivity() {

    var stationList = ""
    var localhost = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.main_editText)
        val switch = findViewById<Switch>(R.id.main_switch)
        val linkaccountbutton = findViewById<Button>(R.id.main_linkbutton)


        val mypreference = MyPreference(this)
        var loginCount = mypreference.getLoginCount()
        loginCount++
        mypreference.setloginCount(loginCount)
        println("THIS MIGHT WORK" + loginCount)

        searched_recylerView.layoutManager = LinearLayoutManager(this)
        getAllStation(localhost)

        switch.setOnCheckedChangeListener { compoundButton, b ->
            println("Switch changed" + b)
            localhost = b

            var usinglocalhost = mypreference.getLocalhost()
            mypreference.setlocalhost(localhost)
            println("THIS MIGHT WORK localhost " + localhost)

            getAllStation(localhost)

        }

        linkaccountbutton.setOnClickListener {
            println("Clicked")
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
                println(s) // s is what's currently in the EditText
//                println(stationList)
                val gson = GsonBuilder().create()

                val listOfStations = gson.fromJson(stationList, listOfStations::class.java)
                println(listOfStations)

                if (listOfStations != null) {
                    if (s.length >= 4) {

                        for ((i, item) in listOfStations.test.withIndex()) {
                            if (listOfStations.test[i].Station_Name.contains(s,ignoreCase = true)) {
                                matchedStations.add("{\"Station_Name\":\"${listOfStations.test[i].Station_Name}\",\"CRS_Code\":\"${listOfStations.test[i].CRS_Code}\"}")
                            }
                        }
                        println("{\"test\":${matchedStations}}")
                        val matchedStationsString = "{\"test\":${matchedStations}}"

                        val gson2 = GsonBuilder().create()

                        val matchedlistOfStations = gson2.fromJson(matchedStationsString, MainActivity.listOfStations::class.java)

                        runOnUiThread {
                            searched_recylerView.adapter = MainAdapterSearch(matchedlistOfStations)
                        }

                        matchedStations = ArrayList()
                    } else {
                        println("HERE")
                        println(listOfStations)
                        val listOfStations = gson.fromJson(stationList, listOfStations::class.java)

                        runOnUiThread {
                            searched_recylerView.adapter = MainAdapterSearch(listOfStations)
                        }

                    }
                }
            }
        })
    }

    fun getAllStation(localhost: Boolean){

        println("This is localhost value" + localhost)

        var url =  "http://10.0.2.2:3000/stations"

        if (!localhost){
            url = "http://vmi285311.contaboserver.net:3000/stations"
        }

        println(url)

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


