package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth

class Home_page : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page2)
        if(FirebaseAuth.getInstance().currentUser!!.email.equals("guptadhaumaiya@gmail.com")){
            intent = Intent(this, AdminHome_Page::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        val take_test=findViewById<LinearLayout>(R.id.take_test)
        val logical_reasoning =findViewById<LinearLayout>(R.id.logical_reasoning)
        val arithmetic_apt =findViewById<LinearLayout>(R.id.arithmetic_apt)
        val programming_ques =findViewById<LinearLayout>(R.id.programming_questions)
        val dbms =findViewById<LinearLayout>(R.id.dbms)
        val upload_ques =findViewById<LinearLayout>(R.id.upload_questions)

        logical_reasoning.setOnClickListener() {
            startActivity(Intent(this,logical_chooser::class.java))
        }
        arithmetic_apt.setOnClickListener(){
            startActivity(Intent(this,arithmetic_chooser::class.java))
        }
        programming_ques.setOnClickListener() {
            startActivity(Intent(this,programming_chooser::class.java))
        }
        take_test.setOnClickListener(){
            startActivity(Intent(this,Test_chooser::class.java))
        }

        upload_ques.setOnClickListener(){
            startActivity(Intent(this,UploadQuestion::class.java))
        }
        dbms.setOnClickListener(){
            val intent=Intent(this,Show::class.java)
            intent.putExtra("type","Database")
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_home_page,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId.toInt()
        when (id) {
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.view_uploaded_questions -> {
                startActivity(Intent(this, ViewUploadedQuestion::class.java))
                Log.v("UPLOADED QUESTION", "watch uploaded questions")
            }
            R.id.total_coins -> {
                startActivity(Intent(this, ShowCoins::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }
}