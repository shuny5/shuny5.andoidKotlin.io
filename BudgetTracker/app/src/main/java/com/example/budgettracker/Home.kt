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
import java.time.LocalDate.*
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
            val tripStart = tripInfo[1]
            val tripEnd = tripInfo[2]
            val balanceRem = tripInfo[3]

            // Calculate total days of trip
            val totalDays = totalDaysLeft(tripStart, tripEnd)


            //display info
            val txtTitle: TextView = findViewById(R.id.tripName)
            val txtRemTotal: TextView = findViewById(R.id.remTotal)
            val txtRemDays: TextView = findViewById(R.id.remDays)
            val txtAverageRem: TextView = findViewById(R.id.remPerDay)

//            val remPerDay = (getTotalPerDay(remDays.toInt(),balanceRem.toInt()))

            txtTitle.text = "Trip to: $tripTitle"
            txtRemTotal.text = "Remaining: ¥$balanceRem"
//            txtRemDays.text = "$remDays Days out of $totalDays"
//            txtAverageRem.text = "¥$remPerDay per Day"

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
    private fun totalDaysLeft(start: String, end: String): Long {
        val split = "T"
        val currentDateTime = LocalDateTime.now()
        val localDateTime = LocalDateTime.parse(currentDateTime.toString())
        val currentDate = localDateTime.toString().split(split)
        val formatter = DateTimeFormatter.ofPattern("mm/DD/yyyy")
//        val formatDate = formatter.format(currentDate)

        val dateNow = currentDate[0]

        val start = parse(start, DateTimeFormatter.ofPattern("mm/DD/yyyy"))
//        val end = parse(dateNow, DateTimeFormatter.ofPattern("mm/DD/yyyy"))
        val end = parse(dateNow, DateTimeFormatter.ofPattern("mm/DD/yyyy"))

//        val end = parse(end, DateTimeFormatter.ofPattern("mmDDyyyy"))
        val delim = "/"

        println(end)

        val starts = start.toString().split(delim)
        val ends = end.toString().split(delim)

        val startYear = starts[0].toInt()
        val startMonth = starts[1].toInt()
        val startDay = starts[2].toInt()
        val endYear = ends[0].toInt()
        val endMonth = ends[1].toInt()
        val endDay = ends[2].toInt()

        val localDateStartDate = of(startYear, startMonth, startDay)
        val localDateEndDate = of(endYear, endMonth, endDay)
        return ChronoUnit.DAYS.between(localDateStartDate, localDateEndDate)
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
            val tripStart = rs.getString(3)
            val tripEnd = rs.getString(4)
            val tripBudget = rs.getString(5)
            return listOf(tripName, tripStart, tripEnd, tripBudget.toString()).toTypedArray()
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
}
