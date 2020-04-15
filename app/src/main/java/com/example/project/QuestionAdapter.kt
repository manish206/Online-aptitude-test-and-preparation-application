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
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ValueEventListener

public class QuestionAdapter() : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    lateinit var context:Context
    lateinit var Question_list: MutableList<Question>


    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question=itemView.findViewById<TextView>(R.id.txt_question)
        val option1=itemView.findViewById<TextView>(R.id.option1)
        val option2=itemView.findViewById<TextView>(R.id.option2)
        val option3=itemView.findViewById<TextView>(R.id.option3)
        val option4=itemView.findViewById<TextView>(R.id.option4)
        val counter=itemView.findViewById<TextView>(R.id.txtView_counter)
        val txtViewAnswer=itemView.findViewById<TextView>(R.id.txtViewAnswer)
        val txtViewComment=itemView.findViewById<TextView>(R.id.txtViewComment)
        val viewAnswer=itemView.findViewById<TextView>(R.id.view_answer)
        val viewDescription=itemView.findViewById<TextView>(R.id.view_description)
    }


    constructor(context: Context, Question_list: MutableList<Question>) : this() {
        this.Question_list=Question_list
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {

        val inflater : LayoutInflater= LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.question_list_layout,parent,false)

        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Question_list.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
       var Q:Question=Question_list.get(position)
        holder.question.text=Q.question.toString()
        holder.option1.text=Q.option1.toString()
        holder.option2.text=Q.option2.toString()
        holder.option3.text=Q.option3.toString()
        holder.option4.text=Q.option4.toString()
        holder.counter.text=("Q."+(position+1).toInt()+"  ").toString()
        var answered:String=Q.answer.toString()
        var description:String=Q.description.toString()

        holder.setIsRecyclable(false)
        holder.option1.setOnClickListener(View.OnClickListener {
            if(holder.option1.text==answered){holder.option1.setTextColor(Color.parseColor("#148302"))}
            else{holder.option1.setTextColor(Color.parseColor("#ff0000"))}
            when(answered){
                holder.option2.text.toString()->holder.option2.setTextColor(Color.parseColor("#148302"))
                holder.option3.text.toString()->holder.option3.setTextColor(Color.parseColor("#148302"))
                holder.option4.text.toString()->holder.option4.setTextColor(Color.parseColor("#148302"))
            }
        })
        holder.option2.setOnClickListener(View.OnClickListener {
            if(holder.option2.text==answered){holder.option2.setTextColor(Color.parseColor("#148302"))}
            else{holder.option2.setTextColor(Color.parseColor("#ff0000"))}
            when(answered){
                holder.option1.text.toString()->holder.option1.setTextColor(Color.parseColor("#148302"))
                holder.option3.text.toString()->holder.option3.setTextColor(Color.parseColor("#148302"))
                holder.option4.text.toString()->holder.option4.setTextColor(Color.parseColor("#148302"))
            }
        })
        holder.option3.setOnClickListener(View.OnClickListener {
            if(holder.option3.text==answered){holder.option3.setTextColor(Color.parseColor("#148302"))}
            else{holder.option3.setTextColor(Color.parseColor("#ff0000"))}
            when(answered){
                holder.option1.text.toString()->holder.option1.setTextColor(Color.parseColor("#148302"))
                holder.option2.text.toString()->holder.option2.setTextColor(Color.parseColor("#148302"))
                holder.option4.text.toString()->holder.option4.setTextColor(Color.parseColor("#148302"))
            }
        })
        holder.option4.setOnClickListener(View.OnClickListener {
            if(holder.option4.text==answered){holder.option4.setTextColor(Color.parseColor("#148302"))}
            else{holder.option4.setTextColor(Color.parseColor("#ff0000"))}
            when(answered){
                holder.option1.text.toString()->holder.option1.setTextColor(Color.parseColor("#148302"))
                holder.option2.text.toString()->holder.option2.setTextColor(Color.parseColor("#148302"))
                holder.option3.text.toString()->holder.option3.setTextColor(Color.parseColor("#148302"))
            }
        })

        holder.txtViewAnswer.setOnClickListener(View.OnClickListener {

            when(answered){
                holder.option1.text.toString()->holder.option1.setTextColor(Color.parseColor("#148302"))
                holder.option2.text.toString()->holder.option2.setTextColor(Color.parseColor("#148302"))
                holder.option3.text.toString()->holder.option3.setTextColor(Color.parseColor("#148302"))
                holder.option4.text.toString()->holder.option4.setTextColor(Color.parseColor("#148302"))
            }
            if((holder.viewAnswer.visibility==View.GONE && holder.viewDescription.visibility==View.GONE)) {
                holder.viewAnswer.visibility = View.VISIBLE
                holder.viewDescription.visibility = View.VISIBLE
                holder.viewAnswer.setText("Answer : " + answered.toString())
                holder.viewDescription.setText("Description : " + description.toString())

            }
            else{
                holder.viewAnswer.visibility =View.GONE
                holder.viewDescription.visibility = View.GONE
            }
        })


        holder.txtViewComment.setOnClickListener(View.OnClickListener {
            val intent:Intent= Intent(context,CommentLogActiity::class.java)
            intent.putExtra("QUESTION_OBJECT",Q)
            context.startActivity(intent)
        })
    }

}