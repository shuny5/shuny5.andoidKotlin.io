package com.example.budgettracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        var tripTitle = ""
        var daysRem = ""
        var balanceRem = ""

        var savedID = loadID()

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val dbInfo = listOf<String>(tripTitle, daysRem, balanceRem).toTypedArray()

        var tripInfo = listOf<String>().toTypedArray()

        val title: TextView = findViewById(R.id.tripName)
        title.text = tripTitle
        val remTotal: TextView = findViewById(R.id.remTotal)
        remTotal.text = balanceRem
        val remDays: TextView = findViewById(R.id.remDays)
        remDays.text = (daysRem)

        val btnAdd: Button = findViewById(R.id.addTrip)
        val btnAddEx: Button = findViewById(R.id.addExpns)
        val btnEdit: Button = findViewById(R.id.editTrip)

        btnAdd.setOnClickListener {
            val newTrip = Intent(this, NewTrip::class.java)
            startActivity(newTrip)
        }

        btnAddEx.setOnClickListener {
            val newItem = Intent(this, Expenses::class.java)
            startActivity(newItem)
        }

//        btnEdit.setOnClickListener{
//            val editTrip = Intent(this, EditTrip::class.java)
//            startActivity(editTrip)
//        }
    }

    fun loadID(): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("INT_ID", 0)
    }

}