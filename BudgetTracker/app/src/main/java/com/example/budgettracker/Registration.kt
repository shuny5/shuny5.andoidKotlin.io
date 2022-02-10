package com.example.budgettracker

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        val btnReg: Button = findViewById(R.id.btn_reg)
        val logIn: TextView = findViewById(R.id.logIn)

        // Register account
        btnReg.setOnClickListener {
            addAccount()
        }
        // Login page
        logIn.setOnClickListener {
            val signUp = Intent(this, Login::class.java)
            startActivity(signUp)
        }
    }

    private fun addAccount() {
        val emailLogin: EditText = findViewById(R.id.emailLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)
        val confirmPass: EditText = findViewById(R.id.confirmPass)

        val email = emailLogin.text.trim().toString()
        val password = passwordLogin.text.trim().toString()
        val passCon = confirmPass.text.trim().toString()

        val regSuccess = Intent(this, Home::class.java)

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase

        // Check if filled in
        if (email.isNotEmpty() and passwordLogin.text.trim()
                .isNotEmpty() and confirmPass.text.trim().isNotEmpty()
        ) {
            // Check if email is unique
            if (uniqueEmail(email)) {
                // Check if passwords match
                if (password == passCon) {
                    val cv = ContentValues()
                    cv.put("EMAIL", email)
                    cv.put("PASSWORD", password)
                    db.insert("USERS", null, cv)
                    // Get user ID to access other user data
                    val userID = getID(email)
                    // Save user ID to be used later
                    saveID(userID)

                    Toast.makeText(this, "Register Complete $userID", Toast.LENGTH_LONG).show()
                    startActivity(regSuccess)
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Email is already Registered", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }

    private fun uniqueEmail(email: String): Boolean {
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val dbInfo = listOf(email).toTypedArray()
        val rs = db.rawQuery("SELECT EMAIL FROM USERS WHERE EMAIL = ?", dbInfo)
        if (!rs.moveToNext()) {
            return true
        }
        rs.close()
        return false
    }

    private fun getID(email: String): Int {
        val id: Int
        val dbInfo = listOf(email).toTypedArray()
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase

        // Use email to fetch USERID
        val selectQuery = "SELECT USERID FROM USERS WHERE EMAIL = ?"
        val rs = db.rawQuery(selectQuery, dbInfo)

        if (rs.moveToNext()) {
            id = rs.getInt(0)
            rs.close()
            return id
        }
        rs.close()
        return -1
    }

    private fun saveID(userID: Int) {

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply {
            putInt("USER_ID", userID)
        }.apply()
    }
}