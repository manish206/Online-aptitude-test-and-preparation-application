package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewUploadedQuestion : AppCompatActivity() {
    lateinit var question:Question
    lateinit var question_list:MutableList<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_uploaded_question)
        question_list= mutableListOf()
        val QuestionRecylerview=findViewById<RecyclerView>(R.id.QuestionRecyclerView)
        QuestionRecylerview.layoutManager=LinearLayoutManager(this)
        FirebaseDatabase.getInstance().getReference("/Questions/").orderByChild("uid").equalTo(FirebaseAuth.getInstance().uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        for(snapShot in p0.children){
                            question= snapShot.getValue(Question::class.java)!!
                            question_list.add(question)
                        }
                    }
                    val adapter=UploadedQuestionAdapter(this@ViewUploadedQuestion,question_list)
                    QuestionRecylerview.adapter=adapter
                }
            })
    }
}
