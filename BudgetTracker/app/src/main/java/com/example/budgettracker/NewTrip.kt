package com.example.budgettracker

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewTrip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_trip)

        val btnSave: Button = findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            createTrip()
        }
    }

    private fun createTrip() {
        val tripName: EditText = findViewById(R.id.tripName)
        val numDays: EditText = findViewById(R.id.numDays)
        val totalAmount: EditText = findViewById(R.id.totalAmount)

        val trip = tripName.text.trim().toString()
        val days = numDays.text.trim().toString()
        val total = totalAmount.text.trim().toString()

        val save = Intent(this, Home::class.java)

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val cv = ContentValues()

        val userID = loadID()

        cv.put("USERID", userID)
        cv.put("TRIP", trip)
        cv.put("DAYS", days)
        cv.put("TOTAL", total)
        db.insert("TRIPS", null, cv)

        Toast.makeText(this, "Trip Created!", Toast.LENGTH_LONG).show()

        val tripID = getTripID(trip)
        Toast.makeText(this, tripID.toString(), Toast.LENGTH_SHORT).show()
        saveTripID(tripID)
        startActivity(save)
    }

    private fun getTripID(trip: String): Int {
        val tID: Int
        val dbInfo = listOf(trip).toTypedArray()
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase

        val selectQuery = "SELECT TRIPID FROM TRIPS WHERE TRIP = ?"
        val rs = db.rawQuery(selectQuery, dbInfo)

        if (rs.moveToNext()) {
            tID = rs.getInt(0)
            rs.close()
            return tID
        }
        rs.close()
        return 0
    }

    private fun saveTripID(tripID: Int) {

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putInt("TRIP_ID", tripID)
        }.apply()
    }

    private fun loadID(): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("USER_ID", 0)
    }
}