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
import java.net.IDN

class Expenses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expenses)

        val btnSave: Button = findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            createExpense()
        }
    }

    private fun createExpense() {
        val itemName: EditText = findViewById(R.id.item)
        val itemPrice: EditText = findViewById(R.id.price)

        val item = itemName.text.trim().toString()
        val price = itemPrice.text.trim().toString()

        val save = Intent(this, Home::class.java)

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val cv = ContentValues()

        cv.put("ITEM", item)
        cv.put("PRICE", price)
        db.insert("ITEMS", null, cv)

        var tripID = loadTripID()
        updateTotal(tripID)

        Toast.makeText(this, "Expense Added!", Toast.LENGTH_LONG).show()
        startActivity(save)
    }
    private fun loadTripID() :Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("TRIP_ID", 0)
    }
    private fun updateTotal(tripID: Int){

    }
}