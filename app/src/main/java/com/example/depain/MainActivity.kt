package com.example.depain

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        main_recylerView.layoutManager = LinearLayoutManager(this)
//        main_recylerView.adapter = MainAdapter()



        searchButton.setOnClickListener{
            val searchText = editText.text.toString()
            if (searchText != ""){
                hideKeyboard()
                Toast.makeText(this,searchText, Toast.LENGTH_SHORT).show()
                // This is where fetchJson should go
//                fetchJson(searchText)
//                railmate
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

        fetchJson("KGX") // Pass into fetchJson searchText to get value from Edit text in testing hard code "KGX"
    }


    private fun hideKeyboard() {
        val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }

    fun fetchJson(Station: String) {

        val appID = "3c4cbb0f"
        val API_KEY = "0d7a9acca38e2ae512ce880393512982"


        val url =
            "https://transportapi.com/v3/uk/train/station/$Station/live.json?app_id=3c4cbb0f&app_key=0d7a9acca38e2ae512ce880393512982&darwin=false&train_status=passenger" // ToDo add real url

        val request = Request.Builder().url(url).build()

        var client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {


            // Geting back responamce json for depatures drom london kings cross
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

