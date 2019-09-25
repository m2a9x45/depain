package com.example.depain

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient







class LinkActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_link)


        val submitButton = findViewById<Button>(R.id.link_button)
        val emailEditText = findViewById<EditText>(R.id.link_editText)

        submitButton.setOnClickListener{
            val email = emailEditText.text.toString()
            println("Entered email $email")

            postData("{\"email\":\"${email}\",\"os\":\"android\"}")
        }

    }

    fun postData(data : String) {

        val JSON = "application/json; charset=utf-8".toMediaType()

        val url = "http://10.0.2.2:3001/interest"

        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaTypeOrNull()

        val request = Request.Builder().url(url).post(data.toRequestBody(JSON)).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                runOnUiThread {
                    val errorText = findViewById<TextView>(R.id.link_error)
                    errorText.text = ""
                }

                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val linkobj = gson.fromJson(body, linkobj::class.java)
                println(linkobj._id)

                runOnUiThread {
                    val errorText = findViewById<TextView>(R.id.link_error)
                    errorText.text = linkobj._id
                }


                if (linkobj._id == null){
                    val errorobj = gson.fromJson(body, errorobj::class.java)
                    println(errorobj.message)

                    runOnUiThread {
                        val errorText = findViewById<TextView>(R.id.link_error)
                        errorText.text = errorobj.message
                    }

                }


            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to excute http request")
                println(e)
            }
        })
    }

    class linkobj(val email : String, val os : String, val date : String, val _id : String)
    class errorobj(val message : String, val error : Boolean)

}




