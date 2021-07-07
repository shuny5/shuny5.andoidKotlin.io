package com.example.budgettracker

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
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

        val register = Intent(this, RegistrationActivity::class.java);

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

        val login = Intent(this, Home::class.java)
        val helper = DatabaseHandler(applicationContext)
        var db = helper.readableDatabase
        var rs: Cursor = db.rawQuery("SELECT * FROM enterEmail ", null)

        if (emailLogin.text.trim().isNotEmpty() and passwordLogin.text.trim().isNotEmpty()) {
            val dbInfo = listOf<String>(enterEmail, enterPass).toTypedArray()
            val rs = db.rawQuery(
                "SELECT * FROM TABLE_ACCOUNT WHERE KEY_EMAIL = ? AND KEY_PASSWORD = ?",
                dbInfo
            )
            if (rs.moveToNext()) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                startActivity(login)
            } else {
                Toast.makeText(this, "Account Not Found", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }
}