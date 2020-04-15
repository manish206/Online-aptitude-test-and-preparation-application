package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class logical_chooser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logical_chooser)

        val number_series=findViewById<LinearLayout>(R.id.number_series)
        val verbal_classification=findViewById<LinearLayout>(R.id.verbal_classification)
        val letter_and_symbol_series=findViewById<LinearLayout>(R.id.letter_and_symbol_series)
        val statement_and_conclusion=findViewById<LinearLayout>(R.id.statement_and_conclusion)
        val cause_and_effect=findViewById<LinearLayout>(R.id.cause_and_effect)
        val logical_problem=findViewById<LinearLayout>(R.id.logical_problem)

        number_series.setOnClickListener(){
            var intent: Intent = Intent(this,Show::class.java)
            intent.putExtra("type","Number series")
            startActivity(intent)
        }
        verbal_classification.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Verbal classification")
            startActivity(intent)
        }
        letter_and_symbol_series.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Letter and symbol series")
            startActivity(intent)
        }
        statement_and_conclusion.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Statement and conclusion")
            startActivity(intent)
        }
        cause_and_effect.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Cause and effect")
            startActivity(intent)
        }
        logical_problem.setOnClickListener(){
            var intent: Intent =Intent(this,Show::class.java)
            intent.putExtra("type","Logical problems")
            startActivity(intent)
        }
    }
}
