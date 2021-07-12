package com.example.budgettracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        var tripTitle = ""
        var daysRem = ""
        var balanceRem = ""

        val tripInfo = listOf(tripTitle, daysRem, balanceRem).toTypedArray()
        getTrip()

        tripTitle = tripInfo[0]
        daysRem = tripInfo[1]
        balanceRem = tripInfo[2]

//        Toast.makeText(this, tripTitle, Toast.LENGTH_LONG).show()
        val title: TextView = findViewById(R.id.tripName)
        title.text = tripTitle
        val remDays: TextView = findViewById(R.id.remDays)
        remDays.text = (daysRem)
        val remTotal: TextView = findViewById(R.id.remTotal)
        remTotal.text = balanceRem
        Toast.makeText(this, balanceRem, Toast.LENGTH_LONG).show()

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

    private fun getTrip(): Array<String> {
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val tripID = loadID().toString()
        val dbInfo = listOf(tripID).toTypedArray()
        val selectQuery = "SELECT * FROM TRIPS WHERE USERID = ?"
        val rs = db.rawQuery(selectQuery, dbInfo)

        if (rs.moveToFirst()) {
            val tripName = rs.getString(2)
            val tripDays = rs.getString(3)
            val tripBudget = rs.getString(4)
            return listOf(tripName, tripDays.toString(), tripBudget.toString()).toTypedArray()
        }
        rs.close()
        return emptyArray()
    }

    private fun loadID(): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("INT_ID", 0)
    }

}