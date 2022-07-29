package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var signup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signup= findViewById(R.id.btnSignup)
        login= findViewById(R.id.btnLogin)
        email= findViewById(R.id.edtEmail)
        password= findViewById(R.id.edtPassword)
        mAuth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            intent = Intent(this@Login,Signup::class.java)
          startActivity(intent)
       }
        login.setOnClickListener {
            val email= email.text.toString()
            val password= password.text.toString()
            if(email.isEmpty() || password.isEmpty())
                Toast.makeText(this,"Enter details",Toast.LENGTH_SHORT).show()
            else
                login(email,password)
        }

    }
    private fun login(email: String,password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}