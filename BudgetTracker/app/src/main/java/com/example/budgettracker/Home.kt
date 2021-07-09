package com.example.budgettracker

import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.os.Bundle
import android.widget.AdapterView
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
        var id = 0

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val dbInfo = listOf<String>(tripTitle, daysRem, balanceRem).toTypedArray()
        //val rs: Cursor = db.rawQuery("SELECT * FROM USERS WHERE USERID LIKE ")

//        var catOne = ""
//        var catTwo = ""
//        var catThree = ""
//
//        var dailyCatOne = catOne.toDouble() / daysRem.toDouble()
//        var dailyCatTwo = catTwo.toDouble() / daysRem.toDouble()
//        var dailyCatThree = catThree.toDouble() / daysRem.toDouble()

        val title: TextView = findViewById(R.id.tripName)
        title.text = tripTitle
        val remTotal: TextView = findViewById(R.id.remTotal)
        remTotal.text = balanceRem
        val remDays: TextView = findViewById(R.id.remDays)
        remDays.text = (daysRem)

        val btnAdd: Button = findViewById(R.id.addTrip)

        btnAdd.setOnClickListener {
            val newTrip = Intent(this, NewTrip::class.java)
            startActivity(newTrip)
        }
    }

}