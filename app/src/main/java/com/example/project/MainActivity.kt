package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val register = findViewById<TextView>(R.id.register)
        val btnok = findViewById<Button>(R.id.ok)
        val forgetPass = findViewById<TextView>(R.id.forget_pass)
        val UserRef:DatabaseReference=FirebaseDatabase.getInstance().getReference().child("users")

        if (FirebaseAuth.getInstance().currentUser != null){
            intent=Intent(this,Home_page::class.java)
            intent.flags=(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finish()
            startActivity(intent)
        }
        else {
            register.setOnClickListener()
            {
                startActivity(Intent(this, register_page::class.java))
            }

            btnok.setOnClickListener {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text?.trim().toString(), password.text.toString()).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            var CurrentUserId=FirebaseAuth.getInstance().currentUser!!.uid.toString()
                            var deviceToken=FirebaseInstanceId.getInstance().getToken()
                            UserRef.child(CurrentUserId).child("device_token").setValue(deviceToken).addOnCompleteListener(
                                OnCompleteListener {
                                    task ->
                                    if(task.isSuccessful){
                                        Toast.makeText(this,"User authenticated successfully!!!",Toast.LENGTH_SHORT).show()
                                    }
                                })

                            if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {

                                intent = Intent(this, Home_page::class.java)
                                intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()

                            } else {
                                Toast.makeText(
                                    this,
                                    "please verify your Email Id",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                task.exception?.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            forgetPass.setOnClickListener()
            {
                startActivity(Intent(this,ForgetPassword::class.java))
            }
        }
   }
}

