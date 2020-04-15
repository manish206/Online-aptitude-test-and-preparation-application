package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class arithmetic_chooser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arithmetic_chooser)
        val  age=findViewById<LinearLayout>(R.id.age)
        val  problem_on_train=findViewById<LinearLayout>(R.id.train)
        val  hcf_and_lcm=findViewById<LinearLayout>(R.id.hcf_and_lcm)
        val  height_and_distances=findViewById<LinearLayout>(R.id.height_and_distances)
        val  time_and_work=findViewById<LinearLayout>(R.id.time_and_work)
        val  percentage=findViewById<LinearLayout>(R.id.percentage)

        age.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Age")
            startActivity(intent)
        }
        problem_on_train.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Problem on train")
            startActivity(intent)
        }
        hcf_and_lcm.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","H.C.F. and L.C.M.")
            startActivity(intent)
        }
        height_and_distances.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Height and distances")
            startActivity(intent)
        }
        time_and_work.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Time and work")
            startActivity(intent)
        }
        percentage.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Percentage")
            startActivity(intent)
        }

    }
}
