package com.example.project

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


public class TestAdapter() : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    lateinit var Question_list:ArrayList<Question>
    lateinit var context:Context
    var score=0
    lateinit var user_answer:IntArray

    constructor(context: Context,Question_list:ArrayList<Question>, user_answer:IntArray):this(){
        this.Question_list=Question_list
        this.context=context
        this.user_answer=user_answer
    }

    class TestViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val question=itemView.findViewById<TextView>(R.id.txt_question)
        val option1=itemView.findViewById<TextView>(R.id.option1)
        val option2=itemView.findViewById<TextView>(R.id.option2)
        val option3=itemView.findViewById<TextView>(R.id.option3)
        val option4=itemView.findViewById<TextView>(R.id.option4)
        val counter=itemView.findViewById<TextView>(R.id.txtView_counter)
        val CheckBox1=itemView.findViewById<CheckBox>(R.id.CheckBox1)
        val CheckBox2=itemView.findViewById<CheckBox>(R.id.CheckBox2)
        val CheckBox3=itemView.findViewById<CheckBox>(R.id.CheckBox3)
        val CheckBox4=itemView.findViewById<CheckBox>(R.id.CheckBox4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.test_view,parent,false)
        return TestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Question_list.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        var Q:Question=Question_list.get(position)

        holder.question.text=Q.question.toString()
        holder.option1.text=Q.option1.toString()
        holder.option2.text=Q.option2.toString()
        holder.option3.text=Q.option3.toString()
        holder.option4.text=Q.option4.toString()
        holder.counter.text=("Q."+(position+1).toInt()+"  ").toString()

        for( i in 0..user_answer.size-1){
            if(position==i)
            {
                if(user_answer[i]!=0){
                    when(user_answer[i])
                    {
                        1->{
                            if(holder.option1.text!=Q.answer.toString()) {
                                holder.option1.setTextColor(Color.parseColor("#ff0000"))
                            }
                            holder.CheckBox1.isChecked=true
                        }
                        2->{
                            if(holder.option2.text!=Q.answer.toString()) {
                                holder.option2.setTextColor(Color.parseColor("#ff0000"))}
                            holder.CheckBox2.isChecked=true
                        }
                        3->{
                            if(holder.option3.text!=Q.answer.toString()) {
                                holder.option3.setTextColor(Color.parseColor("#ff0000"))}
                            holder.CheckBox3.isChecked=true
                        }
                        4->{
                            if(holder.option4.text!=Q.answer.toString()) {
                                holder.option4.setTextColor(Color.parseColor("#ff0000"))}
                            holder.CheckBox4.isChecked=true

                        }
                    }
                }
            }
        }

        holder.CheckBox2.isEnabled=false
        holder.CheckBox3.isEnabled=false
        holder.CheckBox4.isEnabled=false
        holder.CheckBox1.isEnabled=false
        when(Q.answer){
            holder.option1.text->holder.option1.setTextColor(Color.parseColor("#148302"))
            holder.option2.text->holder.option2.setTextColor(Color.parseColor("#148302"))
            holder.option3.text->holder.option3.setTextColor(Color.parseColor("#148302"))
            holder.option4.text->holder.option4.setTextColor(Color.parseColor("#148302"))
        }
    }
}