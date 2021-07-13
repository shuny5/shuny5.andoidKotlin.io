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

class EditTrip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_trip)

        val btnSave: Button = findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            editTrip()
        }
    }

    private fun editTrip() {
        val tripName: EditText = findViewById(R.id.tripName)
        val numDays: EditText = findViewById(R.id.numDays)
        val remTotal: EditText = findViewById(R.id.totalAmount)
        val trip = tripName.text.trim().toString()
        val days = numDays.text.trim().toString()
        val total = remTotal.text.trim().toString()

        val save = Intent(this, Home::class.java)

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val cv = ContentValues()

        val ids = loadID()
        val userID = ids[0]
        val tripID = ids[1]
        Toast.makeText(this, tripID, Toast.LENGTH_SHORT).show()
//        val tripInfo: Array<String> = getTripInfo()
//        if(tripInfo.isNotEmpty()){
//            val tripTitle = tripInfo[0]
//            val totalDays = tripInfo[1]
//            val balanceRem = tripInfo[2]
//        }

//        cv.put("TRIP", trip)
//        cv.put("DAYS", days)
//        cv.put("TOTAL", total)
//        db.insert("TRIPS", null, cv)

        startActivity(save)

    }

    //    private fun getTripInfo():Array<String>{
//        val helper = DatabaseHandler(applicationContext)
//        val db = helper.readableDatabase
//
//        val userID = loadID()
//        val id = userID.toString()
//
//        val dbInfo = listOf(id).toTypedArray()
//        val selectQuery = "SELECT * FROM TRIPS WHERE USERID = ?"
//        val rs = db.rawQuery(selectQuery,dbInfo)
//
//        if(rs.moveToNext()) {
//            val tripName = rs.getString(2)
//            val tripDays = rs.getString(3)
//            val tripBudget = rs.getString(4)
//            return listOf(tripName, tripDays.toString(), tripBudget.toString()).toTypedArray()
//        }
//        rs.close()
//        return emptyArray()
//    }
//    private fun displayInfo(){
//
//    }
    private fun loadID(): List<Int> {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        return listOf<Int>(
            sharedPreferences.getInt("USER_ID", 0),
            sharedPreferences.getInt("TRIP_ID", 0)
        )
    }
}