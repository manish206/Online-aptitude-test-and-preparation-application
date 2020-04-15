package com.example.project

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CommentLogActiity : AppCompatActivity() {

    //public lateinit var comment_list:MutableList<Comment>
    //public lateinit var user_list:MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_log_actiity)

        //val commentRecyclerView=findViewById<RecyclerView>(R.id.recylerCommentview)
        //val editTextMessage=findViewById<EditText>(R.id.editTextMessage)
        //val send_button=findViewById<Button>(R.id.send_button)
       // commentRecyclerView.layoutManager=LinearLayoutManager(this)
        var user:User?=null
        var comment:Comment?=null
        var question : Question =intent.getParcelableExtra("QUESTION_OBJECT") as Question
        var qid:String=question!!.id.toString()
        Log.v("Question  ID",qid)
        var ref=FirebaseDatabase.getInstance().getReference()
        //comment_list= mutableListOf()
        //user_list= mutableListOf()


        //SHOWING QUESTION ON COMMENT ACTIVITY
        val txtviewQuestion=findViewById<TextView>(R.id.txt_question)
        val txtviewOption1=findViewById<TextView>(R.id.option1)
        val txtviewOption2=findViewById<TextView>(R.id.option2)
        val txtviewOption3=findViewById<TextView>(R.id.option3)
        val txtviewOption4=findViewById<TextView>(R.id.option4)
        val txtviwAnswer=findViewById<TextView>(R.id.view_answer)
        val txtiewDescription=findViewById<TextView>(R.id.view_description)

        txtviewQuestion.text=question.question.toString()
        txtviewOption1.text=question.option1.toString()
        txtviewOption2.text=question.option2.toString()
        txtviewOption3.text=question.option3.toString()
        txtviewOption4.text=question.option4.toString()
        txtviwAnswer.text="Answer : "+question.answer.toString()
        txtiewDescription.text=question.description.toString()


        var bundle=Bundle()
        bundle.putString("QID",qid)
        val Comment_fragment=CommentFragment.newInstance(qid)
        val ft=supportFragmentManager.beginTransaction()
        CommentFragment().arguments=bundle
        ft.replace(R.id.frameComment,Comment_fragment).commit()

    }
}


