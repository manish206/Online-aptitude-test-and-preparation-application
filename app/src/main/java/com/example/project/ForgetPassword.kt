package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        val etEmail=findViewById<TextInputEditText>(R.id.email)
        val btnOk=findViewById<Button>(R.id.ok)
        btnOk.setOnClickListener() {
            var email=etEmail.text!!.trim().toString()
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(this,"Open your mail to change the password",Toast.LENGTH_SHORT).show()
                    etEmail.setText("")}
                else{Toast.makeText(this,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()}

            }
        }
    }
}
