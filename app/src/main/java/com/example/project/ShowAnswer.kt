package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ShowAnswer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_answer)
        val showAnswer=findViewById<RecyclerView>(R.id.recyler_answer)
        var intent:Intent=getIntent()

        var question_list:ArrayList<Question> =intent.getParcelableArrayListExtra("QUESTION_LIST")!!
        var CheckboxAnswerArray:IntArray = intent.getIntArrayExtra("USER_ANSWER")
        showAnswer.layoutManager=LinearLayoutManager(this)
        var adapter=TestAdapter(this,question_list,CheckboxAnswerArray)
        showAnswer.adapter=adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent:Intent=Intent(this,Home_page::class.java)
        startActivity(intent)
        finish()
    }
}
