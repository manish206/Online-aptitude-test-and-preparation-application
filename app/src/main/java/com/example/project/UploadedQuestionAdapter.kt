package com.example.project

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class UploadedQuestionAdapter() : RecyclerView.Adapter<UploadedQuestionAdapter.UploadQuestionViewHolder>() {

    lateinit var context: Context
    lateinit var Question_list: MutableList<Question>

    constructor(context: Context, Question_list: MutableList<Question>) : this() {
        this.Question_list=Question_list
        this.context=context
    }
    class UploadQuestionViewHolder(itemView : View) :  RecyclerView.ViewHolder(itemView){
        val question=itemView.findViewById<TextView>(R.id.txt_question)
        val option1=itemView.findViewById<TextView>(R.id.option1)
        val option2=itemView.findViewById<TextView>(R.id.option2)
        val option3=itemView.findViewById<TextView>(R.id.option3)
        val option4=itemView.findViewById<TextView>(R.id.option4)
        val counter=itemView.findViewById<TextView>(R.id.txtView_counter)
        val view_description=itemView.findViewById<TextView>(R.id.view_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadQuestionViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.view_question_layout,parent,false)

        return UploadQuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Question_list.size
    }

    override fun onBindViewHolder(holder: UploadQuestionViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        var Q:Question=Question_list.get(position)
        holder.question.text=Q.question.toString()
        holder.option1.text=Q.option1.toString()
        holder.option2.text=Q.option2.toString()
        holder.option3.text=Q.option3.toString()
        holder.option4.text=Q.option4.toString()
        holder.view_description.text=Q.description.toString()
        holder.counter.text=("Q."+(position+1).toInt()+"  ").toString()
        var answered:String=Q.answer.toString()

        when(answered){
            holder.option1.text.toString()->holder.option1.setTextColor(Color.parseColor("#148302"))
            holder.option2.text.toString()->holder.option2.setTextColor(Color.parseColor("#148302"))
            holder.option3.text.toString()->holder.option3.setTextColor(Color.parseColor("#148302"))
            holder.option4.text.toString()->holder.option4.setTextColor(Color.parseColor("#148302"))
        }

    }
}