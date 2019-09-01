package com.example.depain

import android.content.Context
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
import android.widget.Switch





class MainActivity : AppCompatActivity() {

    var stationList = ""
    var localhost = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.main_editText)
//        val searchButton = findViewById<Button>(R.id.main_serachButton)
        val switch = findViewById<Switch>(R.id.main_switch)


        val mypreference = MyPreference(this)
        var loginCount = mypreference.getLoginCount()
        loginCount++
        mypreference.setloginCount(loginCount)
        println("THIS MIGHT WORK" + loginCount)




        searched_recylerView.layoutManager = LinearLayoutManager(this)
        getAllStation(localhost)
//        main_recylerView_search.adapter = MainAdapterSearch()


//        searched_recylerView.layoutManager = LinearLayoutManager(this)
//        main_recylerView.adapter = MainAdapter()


        switch.setOnCheckedChangeListener { compoundButton, b ->
            println("Switch changed" + b)
            localhost = b

            var usinglocalhost = mypreference.getLocalhost()
            mypreference.setlocalhost(localhost)
            println("THIS MIGHT WORK localhost " + localhost)

            getAllStation(localhost)

        }

//        searchButton.setOnClickListener{
//            val searchText = editText.text.toString()
//            if (searchText != ""){
//                hideKeyboard()
//                Toast.makeText(this,searchText, Toast.LENGTH_SHORT).show()
//                fetchJson(searchText, localhost)
//            }
//        }

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

                if (s.length >= 4) {

                    for ((i, item) in listOfStations.test.withIndex()) {
//                    println(listOfStations.test[i].Station_Name)
                        if (listOfStations.test[i].Station_Name.contains(s,ignoreCase = true)) {
//                            println(listOfStations.test[i].Station_Name)

//                            matchedStations.add(listOfStations.test[i])

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
                }else {
                    println("HERE")
                    println(listOfStations)
                    val listOfStations = gson.fromJson(stationList, listOfStations::class.java)

                    runOnUiThread {
                        searched_recylerView.adapter = MainAdapterSearch(listOfStations)
                    }

                }




            }
        })

//        fetchJson("KGX") // Pass into fetchJson searchText to get value from Edit text in testing hard code "KGX"
    }

//    private fun hideKeyboard() {
//        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
//    }

    fun getAllStation(localhost: Boolean){

        println("This is localhost value" + localhost)

        var url =  "http://10.0.2.2:3000/stations"

        if (localhost == false){
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
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)
            }

        })

    }

    class listOfStations(val test: List<Name>)
    class Name(val Station_Name: String, val CRS_Code: String)



//    fun fetchJson(Station: String, localhost: Boolean) {
//
//        var url = "http://10.0.2.2:3000/livedepatures/${Station}"
//
//        if (localhost == false) {
//            url = "http://de.prettyawful.net:3000/livedepatures/${Station}"
//        }
//
//        println(url)
//
//        val request = Request.Builder().url(url).build()
//
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object : Callback {
//
//
//            // Getting back res json for dep from london kings cross
//            override fun onResponse(call: Call, response: Response) {
//                val body = response.body?.string()
//                println(body)
//                println("Responce got")
//
//                val gson = GsonBuilder().create()
//
//                val DepInfo = gson.fromJson(body, DepInfo::class.java)
//
//                runOnUiThread {
//                    searched_recylerView.adapter = MainAdapter(DepInfo)
//                }
//
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                println("Failed to excute http request")
//                 println(e)
//            }
//        })
//    }

}

//class DepInfo(val departures: departures)
//
//class departures(val all: List<dep>)
//
//class dep(val platform : String, val aimed_departure_time : String, val destination_name : String, val operator_name : String)

