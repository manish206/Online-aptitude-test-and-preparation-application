package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowUserQuestions : AppCompatActivity() {

    lateinit var question_list:MutableList<Question>
    lateinit var users_list:MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user_questions)

        question_list= mutableListOf()
        users_list= mutableListOf()
        val QuestionRecylerview=findViewById<RecyclerView>(R.id.show_user_recyler_view)
        QuestionRecylerview.layoutManager= LinearLayoutManager(this)

        FirebaseDatabase.getInstance().getReference("/Questions/")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        for(snapShot in p0.children){
                            var question= snapShot.getValue(Question::class.java)!!
                            //Log.v("UID",question.id.toString())
                            question_list.add(question)
                        }
                    }

                    FirebaseDatabase.getInstance().getReference("/users/")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                if(p0.exists()){
                                    for(snapShot in p0.children){
                                        var user= snapShot.getValue(User::class.java)!!
                                        // Log.v("UID",user.uid.toString())
                                        users_list.add(user)
                                    }
                                }
                            }
                        })
                    val adapter=QueestionUserAdapter(this@ShowUserQuestions,question_list,users_list)
                    QuestionRecylerview.adapter=adapter
                }
            })

    }
}
