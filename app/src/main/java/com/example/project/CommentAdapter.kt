package com.example.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

public class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    lateinit var context: Context
    lateinit var comment_list: MutableList<Comment>
    lateinit var user_list: MutableList<User>
    lateinit var RecyclerViewClickInterface: RecyclerViewClickListener
    lateinit var U:User
    var reply:Reply?=null

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewCommentMessage = itemView.findViewById<TextView>(R.id.txtViewCommentMessage)
        val txtViewName = itemView.findViewById<TextView>(R.id.txtViewName)
        val txtViewTime = itemView.findViewById<TextView>(R.id.txtViewTime)
        val ImageViewReply=itemView.findViewById<ImageView>(R.id.imageViewReply)
        val ReplyLayout=itemView.findViewById<LinearLayout>(R.id.replyLayout)
        val replyCounter=itemView.findViewById<TextView>(R.id.replyCounter)
       public val RecyclerviewReply=itemView.findViewById<RecyclerView>(R.id.recylerReplyView)
        
    }

    constructor(
        context: Context,
        comment_list: MutableList<Comment>,
        user_list: MutableList<User>,
        RecyclerViewClickInterface : RecyclerViewClickListener
    ) : this() {
        this.context = context
        this.comment_list = comment_list
        this.user_list = user_list
        this.RecyclerViewClickInterface=RecyclerViewClickInterface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comment_list.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        var C: Comment = comment_list.get(position)
            Log.v("COMMENT ID",C.uid.toString())
           // Log.v("USER ID",U.uid.toString())
        for(user in user_list){
            if(user.uid.toString()==C.uid.toString()){

                holder.txtViewCommentMessage.text = C.comment.toString()
                holder.txtViewName.text = user.name.toString()
                holder.txtViewTime.text = C.comment_time.toString()
                U=user

            }
        }


        // Making reply recycler view as visible and showing all the reply and can write the reply
        holder.ImageViewReply.setOnClickListener(View.OnClickListener {

            var cid:String=C.cid.toString()
            var bundle=Bundle()
            bundle.putSerializable("COMMENT",C)
            bundle.putSerializable("User",U)

            val frag: FragmentTransaction =(context as CommentLogActiity).supportFragmentManager.beginTransaction()
            ReplyFragment().arguments=bundle
            frag.replace(R.id.frameComment,ReplyFragment.newInstance(C,U))
            Log.v("Excute","Excute")

            RecyclerViewClickInterface.onClickMessageIconClick(C,U)


        })
        holder.replyCounter.setOnClickListener(View.OnClickListener {

            var cid:String=C.cid.toString()
            var bundle=Bundle()
            bundle.putSerializable("COMMENT",C)
            bundle.putSerializable("User",U)

            val frag: FragmentTransaction =(context as CommentLogActiity).supportFragmentManager.beginTransaction()
            ReplyFragment().arguments=bundle
            frag.replace(R.id.frameComment,ReplyFragment.newInstance(C,U))
            Log.v("Excute","Excute")

            RecyclerViewClickInterface.onClickMessageIconClick(C,U)


        })

    }
}
