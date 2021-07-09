package com.example.budgettracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.btnLogin)
        val signupReg: TextView = findViewById(R.id.loginReg)

        val register = Intent(this, RegistrationActivity::class.java)


        button.setOnClickListener {
            checkAccount()
        }
        signupReg.setOnClickListener {
            startActivity(register)
        }
    }

    private fun checkAccount() {
        val emailLogin: EditText = findViewById(R.id.emailLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)
        val enterEmail = emailLogin.text.trim().toString()
        val enterPass = passwordLogin.text.trim().toString()
        var userID: Int

        val login = Intent(this, Home::class.java)
        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase

        if (emailLogin.text.trim().isNotEmpty() and passwordLogin.text.trim().isNotEmpty()) {
            val dbInfo = listOf<String>(enterEmail, enterPass).toTypedArray()
            val rs =
                db.rawQuery("SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?", dbInfo)
            if (rs.moveToNext()) {

                //get ID with email credentials
                userID = getID(enterEmail)

                //save ID
                saveID(userID)

                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                rs.close()

                //go to home page
                startActivity(login)
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }
    private fun getID(enterEmail: String): Int {
       // val dbInfo = listOf<String>(enterEmail).toTypedArray()
        var id: Int

        val helper = DatabaseHandler(applicationContext)
        val db = helper.readableDatabase
        val selectQuery = "SELECT * FROM USERS WHERE USERID = ?"
        var cursor: Cursor? = null

        cursor = db.rawQuery(selectQuery, null)
        id = cursor.getInt(1)

        cursor.close()
        return id
    }
    fun saveID(userID: Int){

        val savedID: Int = 0

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putInt("INT_ID", savedID)
        }
    }
}