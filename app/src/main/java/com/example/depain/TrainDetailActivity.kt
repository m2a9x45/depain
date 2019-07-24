package com.example.depain

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class TrainDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_traindetails)


        val navbarTitle = intent.getStringExtra("actionbarTitle")

        supportActionBar?.title = navbarTitle

    }
}