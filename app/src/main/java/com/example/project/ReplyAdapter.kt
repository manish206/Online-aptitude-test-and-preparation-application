package com.example.project

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReplyAdapter() : RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder>() {

    lateinit var context: Context
    lateinit var reply_list: MutableList<Reply>
    lateinit var user_list: MutableList<User>
    lateinit var comment: Comment

    class ReplyViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView){
        var txtViewName=itemView.findViewById<TextView>(R.id.txtViewName)
        var txtViewTime=itemView.findViewById<TextView>(R.id.txtViewTime)
        var txtViewReply=itemView.findViewById<TextView>(R.id.txtViewReplyMessage)

    }
    public constructor(context:Context,reply_list: MutableList<Reply>,user_list:MutableList<User>,comment:Comment):this(){
        this.context=context
        this.reply_list=reply_list
        this.user_list=user_list
        this.comment=comment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.reply_view, parent, false)
        return ReplyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reply_list.size
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        Log.v("Reply","ReplyAddd")
        //var REPLY:Reply=reply_list.get(position)
        //var cid:String=C.cid.toString()
        var R: Reply = reply_list.get(position)
        //Log.v("COMMENT ID",C.uid.toString())
        // Log.v("USER ID",U.uid.toString())
        for(user in user_list){

            if(comment.cid==R.cid){
                for(user in user_list){
                    if(R.uid==user.uid){
                        holder.txtViewName.text=user.name
                        holder.txtViewReply.text=R.reply_text
                        holder.txtViewTime.text=R.rTime
                    }

                }
            }
        }

    }
}