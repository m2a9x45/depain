package com.example.depain

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrainDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_traindetails)


        val navbarTitle = intent.getStringExtra("actionbarTitle")
        val aimedDepTime = intent.getStringExtra("aimedDepTime")
        val platform = intent.getStringExtra("platform")
        val operator_name = intent.getStringExtra("operator_name")

        supportActionBar?.title = navbarTitle


        findViewById<TextView>(R.id.textView_deptime).text = "Leaving at " + aimedDepTime
        findViewById<TextView>(R.id.textView_opname).text = "run by " + operator_name


    }
}