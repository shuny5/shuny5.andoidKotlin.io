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
import java.nio.channels.SelectableChannel

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

        var tripID = loadTripID()

        cv.put("TRIPID", tripID)
        cv.put("ITEM", item)
        cv.put("PRICE", price)
        db.insert("ITEMS", null, cv)

        var total = getTotal()
        updateTotal(total, price.toInt(), tripID)

        Toast.makeText(this, "Expense Added!", Toast.LENGTH_LONG).show()
        startActivity(save)
    }

    private fun loadTripID(): Int {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("TRIP_ID", 0)
    }

    private fun getTotal(): Int {
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val dbInfo = listOf(loadTripID().toString()).toTypedArray()

        val selectQuery = "SELECT TOTAL FROM TRIPS WHERE TRIPID = ?"
        val rs = db.rawQuery(selectQuery, dbInfo)

        if (rs.moveToNext()) {
            val newTotal = rs.getInt(0)
            rs.close()
            return newTotal
        }
        return 0
    }

    private fun updateTotal(total: Int, price: Int, tripID: Int) {
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val cv = ContentValues()

        val newTotal = total - price

        cv.put("TOTAL", newTotal)
        db.update("TRIPS", cv, "TOTAL", null)
    }
}