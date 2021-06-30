package com.example.budgettracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        val btnReg: Button = findViewById(R.id.btn_reg)
        val emailLogin: EditText = findViewById(R.id.emailLogin)
        val passwordLogin: EditText = findViewById(R.id.passwordLogin)
        val confirmPass: EditText = findViewById(R.id.confirmPass)
        val logIn: TextView = findViewById(R.id.logIn)


        btnReg.setOnClickListener{

            if(emailLogin.text.trim().isNotEmpty() && passwordLogin.text.trim().isNotEmpty() && confirmPass.text.trim().isNotEmpty()){
                if(passwordLogin.text == confirmPass.text){ //Code is wrong
                    Toast.makeText(this, "Register Complete", Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Password does not match",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Input Required", Toast.LENGTH_LONG).show()
            }
        }
        logIn.setOnClickListener {
            startActivity(intent)
        }
    }
}