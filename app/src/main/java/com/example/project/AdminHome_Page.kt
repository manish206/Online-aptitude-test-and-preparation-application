package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth

class AdminHome_Page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home__page)


        val view_total_users=findViewById<LinearLayout>(R.id.view_total_users)
        val take_test=findViewById<LinearLayout>(R.id.take_test)
        val logical_reasoning =findViewById<LinearLayout>(R.id.logical_reasoning)
        val arithmetic_apt =findViewById<LinearLayout>(R.id.arithmetic_apt)
        val programming_ques =findViewById<LinearLayout>(R.id.programming_questions)
        val dbms =findViewById<LinearLayout>(R.id.dbms)
        val upload_ques =findViewById<LinearLayout>(R.id.upload_questions)


        view_total_users.setOnClickListener(){
            startActivity(Intent(this,View_total_users::class.java))
        }
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
        menuInflater.inflate(R.menu.admin_home_page_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawerLayout=findViewById<DrawerLayout>(R.id.drawLayout)
        var mToggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        if(mToggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId){
            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,MainActivity::class.java))
                return true
            }
            R.id.view_uploaded_questions -> {
                startActivity(Intent(this,ViewUploadedQuestion::class.java))
                Log.v("UPLOADED QUESTION","watch uploaded questions")
                return true
            }
            R.id.total_coins->{
                startActivity(Intent(this,ShowCoins::class.java))
                return true
            }
            R.id.view_users_uploaded_questions->{
                startActivity(Intent(this,ShowUserQuestions::class.java))
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }
}