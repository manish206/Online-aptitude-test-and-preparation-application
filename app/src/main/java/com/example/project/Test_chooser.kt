package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_programming_chooser.view.*
import kotlinx.android.synthetic.main.activity_test_chooser.*

class Test_chooser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_chooser)
        val checkBoxAge=findViewById<CheckBox>(R.id.checkbox_age)
        val checkBoxHcfLcm=findViewById<CheckBox>(R.id.checkbox_hcf_lcm)
        val checkBoxHeightDistance=findViewById<CheckBox>(R.id.checkbox_height_distance)
        val checkBoxPercentage=findViewById<CheckBox>(R.id.checkbox_percentage)
        val checkBoxTimeWork=findViewById<CheckBox>(R.id.checkbox_time_work)
        val checkBoxTrain=findViewById<CheckBox>(R.id.checkbox_train)
        val SubmitButton=findViewById<Button>(R.id.start)


        SubmitButton.setOnClickListener(View.OnClickListener {
            var i:Int=0

            var data : MutableList<String> = mutableListOf()
            if(checkBoxAge.isChecked){
                data.add("Age")
            }
            if(checkBoxHcfLcm.isChecked){
                data.add("H.C.F. and L.C.M.")
            }
            if(checkBoxHeightDistance.isChecked){
                data.add("Height and distances")
            }
            if(checkBoxPercentage.isChecked){
                data.add("Percentage")
            }
            if(checkBoxTimeWork.isChecked){
                data.add("Time and work")
            }
            if(checkBoxTrain.isChecked){
                data.add("Problem on train")
            }
            if(checkbox_pythone.isChecked){
                data.add("Python")
            }
            if(checkbox_java.isChecked){
                data.add("Java")
            }
            if(checkbox_number_series.isChecked){
                data.add("Number series")
            }
            if(checkbox_verbal_classification.isChecked){
                data.add("Verbal classification")
            }
            if(checkbox_letter_symbol_series.isChecked){
                data.add("Letter and symbol series")
            }
            if(checkbox_statement_conclusion.isChecked){
                data.add("Statement and conclusion")
            }
            if(checkbox_cause_effect.isChecked){
            data.add("Cause and effect")
            }
            if(checkbox_logical_problems.isChecked){
                data.add("Logical problems")
            }

            val intent:Intent=Intent(this,Test::class.java)
            intent.putStringArrayListExtra("data", ArrayList(data))
            startActivity(intent)
        })
    }
}
