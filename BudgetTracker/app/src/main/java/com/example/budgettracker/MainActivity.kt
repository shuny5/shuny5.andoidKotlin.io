package com.example.budgettracker

import android.content.Intent
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
        val emailLogin: EditText = findViewById(R.id.emailLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)
        val signupReg: TextView = findViewById(R.id.loginReg)

        button.setOnClickListener {

            if (emailLogin.text.trim().isNotEmpty() && passwordLogin.text.trim().isNotEmpty()) {

                Toast.makeText(this, "Input Received", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }
        }
        signupReg.setOnClickListener {

            val intent = Intent(this, RegistrationActivity::class.java);
            startActivity(intent)
        }
    }
}