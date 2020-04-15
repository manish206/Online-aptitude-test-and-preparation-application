package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class programming_chooser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programming_chooser)

        val cpp=findViewById<LinearLayout>(R.id.cpp)
        val java=findViewById<LinearLayout>(R.id.java)
        val python=findViewById<LinearLayout>(R.id.python)

        python.setOnClickListener(){
            var intent=Intent(this,Show::class.java)
            intent.putExtra("type","Python")
            startActivity(intent)
        }
        java.setOnClickListener(){
            var intent:Intent=Intent(this,Show::class.java)
            intent.putExtra("type","Java")
            startActivity(intent)
        }
        cpp.setOnClickListener(){
            var intent:Intent=Intent(this,Show::class.java)
            intent.putExtra("type","C/C++")
            startActivity(intent)
        }
    }
}
