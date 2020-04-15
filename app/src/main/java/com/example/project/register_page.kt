package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register_page.*

class register_page : AppCompatActivity() {


    var mAuth:FirebaseAuth?=null
    var mdatabase:FirebaseDatabase?=null
    var mDatabaseRefrence:DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val back_to_sign_in=findViewById<TextView>(R.id.back_to_sign_in)
        val submit=findViewById<Button>(R.id.submit)

        mdatabase= FirebaseDatabase.getInstance()
        mDatabaseRefrence= mdatabase!!.reference
        mAuth=FirebaseAuth.getInstance()

        back_to_sign_in.setOnClickListener() {
            intent=Intent(this,MainActivity::class.java)
            FirebaseAuth.getInstance().signOut()
            startActivity(intent)
        }
        submit.setOnClickListener()
        {
            createAccount()
        }

    }
    private fun createAccount()
    {
        val et_email=findViewById<TextInputEditText>(R.id.email)
        val et_pass=findViewById<TextInputEditText>(R.id.password)
        val et_conf_pass=findViewById<TextInputEditText>(R.id.conf_password)

        var email=et_email.text?.trim().toString()
        var pass=et_pass.text.toString()
        var conf_pass=et_conf_pass.text.toString()

        if(!(email.isEmpty() || pass.isEmpty() || conf_pass.isEmpty()))
        {

            val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            if(! (EMAIL_REGEX.toRegex().matches(email))) {
                et_email.setError("Enter valid email ID")
                return}
            if(pass.toString().length<6) {et_pass.setError("password is too short!")}
            if(! (pass.toString().equals(conf_pass.toString())))
            {
                et_conf_pass.setError("Enter correct password")
                return}
            /***
             * email verification
             **/
            verifyEmail(email,pass)
            FirebaseAuth.getInstance().signOut()
        }
        else { Toast.makeText(this,"Enter all the details",Toast.LENGTH_SHORT).show() }

    }
    private fun verifyEmail(email:String,pass:String)
    {
        val et_email=findViewById<TextInputEditText>(R.id.email)
        val et_pass=findViewById<TextInputEditText>(R.id.password)
        val et_conf_pass=findViewById<TextInputEditText>(R.id.conf_password)
        val et_name=findViewById<TextInputEditText>(R.id.name)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this)
        { task ->
            if (task.isSuccessful) {
                FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnCompleteListener { task->
                    if(task.isSuccessful)
                    {Toast.makeText(this,"Registered successfully, check your mail for verification",Toast.LENGTH_SHORT).show()
                        if(FirebaseAuth.getInstance().currentUser?.uid!=null){
                            saveUserToFirebaseDatabase()
                            et_email.text?.clear()
                            et_pass.text?.clear()
                            et_conf_pass.text?.clear()
                            et_name.text?.clear()
                        }
                    }
                    else
                    { Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show() }
                }
            }
            else
            { Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show() }
        }
    }
    //remaining
    private fun saveUserToFirebaseDatabase()
    {
        val name=findViewById<TextInputEditText>(R.id.name)
        val uid=FirebaseAuth.getInstance().currentUser?.uid
        var device_token:String=FirebaseInstanceId.getInstance().getToken().toString()
        val user = User(uid.toString(), name.text.toString(),device_token)
        if(uid.toString()!=null) {

            //saving name to firebase database
            Log.v("USER NAME",user.name)
            FirebaseDatabase.getInstance().getReference("/users/$uid").setValue(user)
                .addOnCompleteListener { Toast.makeText(this,"User data saved successfully",Toast.LENGTH_SHORT).show() }
        }
        else
        {
            Log.d("RegisterPage","User Data not saved ")
        }
    }
}