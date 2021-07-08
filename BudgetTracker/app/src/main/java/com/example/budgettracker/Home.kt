package com.example.budgettracker

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        var tripTitle = ""
        var daysRem = ""

        val btnAdd: Button = findViewById(R.id.addTrip)

        val newTrip = Intent(this, NewTrip::class.java)

        btnAdd.setOnClickListener {
            startActivity(newTrip)
        }
    }

}