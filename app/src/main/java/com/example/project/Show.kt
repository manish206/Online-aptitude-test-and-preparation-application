package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Show : AppCompatActivity() {

    public lateinit var question:Question
    lateinit var ToastBack:Toast
    var backPressedTime:Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val QuestionRecylerview=findViewById<RecyclerView>(R.id.recyler_view)
        var question_list= mutableListOf<Question>()
        QuestionRecylerview.layoutManager=LinearLayoutManager(this)
        var type:String=intent.getStringExtra("type")
        Log.v("Type ",type)

        FirebaseDatabase.getInstance().getReference("/Questions/").orderByChild("type").equalTo(type)
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        for(snapShot in p0.children){
                            question= snapShot.getValue(Question::class.java)!!
                            Log.v("QID",question.id.toString())
                            question_list.add(question)
                        }
                    }
                    val adapter=QuestionAdapter(this@Show,question_list)

                    QuestionRecylerview.adapter=adapter
                }
            })
    }

    override fun onBackPressed() {

        if(backPressedTime+2000>System.currentTimeMillis()){
            super.onBackPressed()
            return
        }
        else{
            ToastBack= Toast.makeText(this,"Press again to exit", Toast.LENGTH_SHORT)
            ToastBack.show()        }
        backPressedTime=System.currentTimeMillis()
    }

}