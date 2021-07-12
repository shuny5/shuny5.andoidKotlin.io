package com.example.budgettracker

import android.app.TaskStackBuilder
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, "USERDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable =
            "CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT, PASSWORD TEXT)"
        val createTripTable =
            "CREATE TABLE TRIPS(TRIPID INTEGER PRIMARY KEY AUTOINCREMENT, USERID INTEGER, TRIP TEXT, DAYS INTEGER, TOTAL INTEGER)"
        val createExpenseTable = "CREATE TABLE ITEMS(TRIPID INTEGER, ITEM TEXT, PRICE INTEGER)"

        db?.execSQL(createTripTable)
        db?.execSQL(createUserTable)
        db?.execSQL(createExpenseTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}