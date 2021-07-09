package com.example.budgettracker

import android.content.ContentValues
import android.content.Intent
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

//        cv.put("USERID", userID)
        cv.put("TRIPS", trip)
        cv.put("DAYS", days)
        cv.put("TOTAL", total)
        db.insert("TRIPS", null, cv)

        Toast.makeText(this, "Trip Created!", Toast.LENGTH_LONG).show()
        startActivity(save)
    }
}