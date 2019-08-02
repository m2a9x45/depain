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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.main_editText)
        val searchButton = findViewById<Button>(R.id.main_serachButton)
        val switch = findViewById<Switch>(R.id.main_switch)
        var localhost = false

        main_recylerView.layoutManager = LinearLayoutManager(this)
//        main_recylerView.adapter = MainAdapter()


        switch.setOnCheckedChangeListener { compoundButton, b ->
            println("Switch changed" + b)
            localhost = b
        }



        searchButton.setOnClickListener{
            val searchText = editText.text.toString()
            if (searchText != ""){
                hideKeyboard()
                Toast.makeText(this,searchText, Toast.LENGTH_SHORT).show()
                fetchJson(searchText, localhost)
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                println(s) // s is what's currently in the EditText
            }
        })

//        fetchJson("KGX") // Pass into fetchJson searchText to get value from Edit text in testing hard code "KGX"
    }


    private fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }

    fun fetchJson(Station: String, localhost: Boolean) {

        var url = "http://10.0.2.2:3000/livedepatures/${Station}"

        if (localhost == false) {
            val appID = "3c4cbb0f"
            val API_KEY = "0d7a9acca38e2ae512ce880393512982"

            url = "https://transportapi.com/v3/uk/train/station/$Station/live.json?app_id=3c4cbb0f&app_key=0d7a9acca38e2ae512ce880393512982&darwin=false&train_status=passenger"
        }

        println(url)

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {


            // Getting back res json for dep from london kings cross
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                println("Responce got")

                val gson = GsonBuilder().create()

                val DepInfo = gson.fromJson(body, DepInfo::class.java)

                runOnUiThread {
                    main_recylerView.adapter = MainAdapter(DepInfo)
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

class dep(val platform : String, val aimed_departure_time : String, val destination_name : String, val operator_name : String)

