package com.example.budgettracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.service.autofill.UserData
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        val btnReg: Button = findViewById(R.id.btn_reg)
        val logIn: TextView = findViewById(R.id.logIn)

        //register account
        btnReg.setOnClickListener {
            addAccount()
        }
        //login page
        logIn.setOnClickListener {
            val signUp = Intent(this, MainActivity::class.java)
            startActivity(signUp)
        }
    }

    private fun addAccount() {
        val emailLogin: EditText = findViewById(R.id.emailLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)
        val confirmPass: EditText = findViewById(R.id.confirmPass)

//        val email = emailLogin.text.trim().toString()
        val password = passwordLogin.text.trim().toString()
        val passCon = confirmPass.text.trim().toString()

        //val databaseHandler = Database(this)

        if (emailLogin.text.trim().isNotEmpty() and passwordLogin.text.trim()
                .isNotEmpty() and confirmPass.text.trim().isNotEmpty()
        ) {
            if (password == passCon) {
                //val status = databaseHandler.addUser(UserDatabase(0, email, password))
                Toast.makeText(this, "Register Complete", Toast.LENGTH_LONG).show()
                val logSuccess = Intent(this, Home::class.java)
                startActivity(logSuccess)
            } else {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
        }
    }
}