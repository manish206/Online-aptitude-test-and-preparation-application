package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

public class QueestionUserAdapter() : RecyclerView.Adapter<QueestionUserAdapter.QuestionUserViewHolderAdapter>() {
    lateinit var context:Context
    lateinit var Question_list: MutableList<Question>
    lateinit var user_list: MutableList<User>


    constructor(context: Context, Question_list: MutableList<Question>,user_list:MutableList<User>) : this() {
        this.Question_list=Question_list
        this.context=context
        this.user_list=user_list
    }

    class QuestionUserViewHolderAdapter(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val question=itemView.findViewById<TextView>(R.id.txt_question)
        val option1=itemView.findViewById<TextView>(R.id.option1)
        val option2=itemView.findViewById<TextView>(R.id.option2)
        val option3=itemView.findViewById<TextView>(R.id.option3)
        val option4=itemView.findViewById<TextView>(R.id.option4)
        val counter=itemView.findViewById<TextView>(R.id.txtView_counter)
        val view_description=itemView.findViewById<TextView>(R.id.view_description)
        val question_uploader_name=itemView.findViewById<TextView>(R.id.question_uploader_name)
        val delete=itemView.findViewById<TextView>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueestionUserAdapter.QuestionUserViewHolderAdapter {

        val inflater : LayoutInflater= LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.view_question_layout,parent,false)

        return QuestionUserViewHolderAdapter(view)
    }

    override fun getItemCount(): Int {
        return Question_list.size
    }


    override fun onBindViewHolder(holder: QuestionUserViewHolderAdapter, position: Int) {
        holder.setIsRecyclable(false)
        var Q:Question=Question_list.get(position)
        for(U in user_list){
            if(Q.uid.equals(U.uid.toString()))
            {
                holder.question_uploader_name.visibility=View.VISIBLE
                holder.question_uploader_name.text="By "+U.name.toString()
                holder.delete.visibility=View.VISIBLE
            }
        }
        holder.question.text=Q.question.toString()
        holder.option1.text=Q.option1.toString()
        holder.option2.text=Q.option2.toString()
        holder.option3.text=Q.option3.toString()
        holder.option4.text=Q.option4.toString()
        holder.view_description.text=Q.description.toString()
        holder.counter.text=("Q."+(position+1).toInt()+"  ").toString()
        Log.v("Question number"+position+1,Q.question)
        var answered:String=Q.answer.toString()

        when(answered){
            holder.option1.text.toString()->holder.option1.setTextColor(Color.parseColor("#148302"))
            holder.option2.text.toString()->holder.option2.setTextColor(Color.parseColor("#148302"))
            holder.option3.text.toString()->holder.option3.setTextColor(Color.parseColor("#148302"))
            holder.option4.text.toString()->holder.option4.setTextColor(Color.parseColor("#148302"))
        }
        holder.delete.setOnClickListener(View.OnClickListener {


            var builder= AlertDialog.Builder(context)
                builder.setMessage("Are you sure you want to delete the question?")

            builder.setTitle("App says")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes"){
                    dialog, which ->

                var qid:String=Q.id.toString()
                FirebaseDatabase.getInstance().getReference("/Questions/").child(qid).removeValue().addOnSuccessListener {
                    Toast.makeText(context,"Question deleted successfully !!!",Toast.LENGTH_SHORT).show()
                for(U in user_list){
                    if(U.uid.equals(Q.uid.toString())){

                        FirebaseDatabase.getInstance().getReference("/reward/").orderByChild("uid").equalTo(U.uid).addValueEventListener(object : ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                            override fun onDataChange(p0: DataSnapshot) {
                                do{
                                    FirebaseDatabase.getInstance().getReference("/reward/").child(p0.toString()).removeValue()
                                }while (false) }
                        })
                    }
                }
            }
            }
            builder.setNegativeButton("No"){
                    dialog, which ->
                return@setNegativeButton
            }
            var resultDialog=builder.create()
            resultDialog.show()
        })

    }
}