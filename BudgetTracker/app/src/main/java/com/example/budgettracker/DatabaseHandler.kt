package com.example.budgettracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.service.autofill.UserData

class Database(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase"
        private const val TABLE_USERS = "UserTable"

        private const val KEY_ID = "_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }

    //create table
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE =
            ("CREATE TABLE " + TABLE_USERS + "(" + KEY_ID + " INTEGER PRIMARY KEY" + KEY_EMAIL + " TEXT," + KEY_PASSWORD + " TEXT" + ")")
        db?.execSQL(CREATE_USER_TABLE)
    }

    //update table
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        onCreate(db)
    }

    //write into table
    fun addUser(emp: UserDatabase): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, emp.email)
        contentValues.put(KEY_PASSWORD, emp.password)

        val success = db.insert(TABLE_USERS, null, contentValues)

        db.close()
        return success
    }
}

