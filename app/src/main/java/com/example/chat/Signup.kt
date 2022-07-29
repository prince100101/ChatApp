package com.example.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signup = findViewById(R.id.btnSsignup)
        name = findViewById(R.id.edtSname)
        email = findViewById(R.id.edtSemail)
        password = findViewById(R.id.edtSpass)
        mAuth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val name = name.text.toString()
            if (email.isNotEmpty()&& password.isNotEmpty()&&name.isNotEmpty())
                signup(email, password, name)
            else
                Toast.makeText(this,"Enter details",Toast.LENGTH_SHORT).show()
        }
    }

    private fun signup(email: String, password: String, name: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser!!.uid)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("User").child(uid).setValue(User(name, email, uid))

    }
}