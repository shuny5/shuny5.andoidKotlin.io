package com.example.budgettracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class Home : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        //get trip info to display
        val tripInfo: Array<String> = getTripInfo()
        if (tripInfo.isNotEmpty()) {
            val tripTitle = tripInfo[0]
            val totalDays = tripInfo[1]
            val balanceRem = tripInfo[2]

            val date = getLocalDate()
//            Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
//            var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
//            var endDate = LocalDate.parse("7-15-2021", formatter)
//
//            var remDays = getNumDays(getDateFromString(date, "MM/dd/yyyy"),
//                endDate)
//            var remPerDay: Int = getTotalPerDay(remDays.toInt(), balanceRem.toInt())

            //display info
            val txtTitle: TextView = findViewById(R.id.tripName)
            val txtRemTotal: TextView = findViewById(R.id.remTotal)
            val txtRemDays: TextView = findViewById(R.id.remDays)
            val txtAverageRem: TextView = findViewById(R.id.remPerDay)

            val remDays = 5
            val remPerDay = (getTotalPerDay(remDays,balanceRem.toInt()))

            txtTitle.text = "Trip to: $tripTitle"
            txtRemTotal.text = "Remaining: ¥$balanceRem"
            txtRemDays.text = "$remDays Days out of $totalDays"
            txtAverageRem.text = "¥$remPerDay per Day"
            //default when table is empty
        } else {
            val tripTitle = "Create a new Trip!"
            val daysRem = "Days Left: 0"
            val balanceRem = "Total: ¥0"
            val remPerDay = "Balance Per Day: ¥0"

            //display info
            val txtTitle: TextView = findViewById(R.id.tripName)
            val txtRemTotal: TextView = findViewById(R.id.remTotal)
            val txtRemDays: TextView = findViewById(R.id.remDays)
            val txtAverageRem: TextView = findViewById(R.id.remPerDay)
            txtTitle.text = tripTitle
            txtRemTotal.text = balanceRem
            txtRemDays.text = daysRem
            txtAverageRem.text = remPerDay
        }


        val btnAdd: Button = findViewById(R.id.addTrip)
        val btnAddEx: Button = findViewById(R.id.addExpns)
        val btnEdit: Button = findViewById(R.id.editTrip)

        //create new trip page
        btnAdd.setOnClickListener {
            val newTrip = Intent(this, NewTrip::class.java)
            startActivity(newTrip)
        }
        //add expense page
        btnAddEx.setOnClickListener {
            val newItem = Intent(this, Expenses::class.java)
            startActivity(newItem)
        }
        //edit trip page
        btnEdit.setOnClickListener {
            val editTrip = Intent(this, EditTrip::class.java)
//            startActivity(editTrip)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLocalDate(): String {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("mm-dd-yyyy")
        return current.format(formatter)
    }

    private fun getTripInfo(): Array<String> {
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase

        val userID = loadID()
        val id = userID.toString()

        val dbInfo = listOf(id).toTypedArray()
        val selectQuery = "SELECT * FROM TRIPS WHERE USERID = ?"
        val rs = db.rawQuery(selectQuery, dbInfo)

        if (rs.moveToNext()) {
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
        return sharedPreferences.getInt("USER_ID", 0)
    }

    private fun getTotalPerDay(remDays: Int, totalLeft: Int): Int {
        return totalLeft / remDays
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNumDays(today: LocalDate, lastDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(today, lastDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateFromString(date: String, format: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format))
    }
}