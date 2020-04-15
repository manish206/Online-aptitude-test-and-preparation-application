package com.example.project

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CommentFragment : Fragment(), RecyclerViewClickListener{

    public lateinit var comment_list:MutableList<Comment>
    public lateinit var user_list:MutableList<User>
    public lateinit var qid:String
    lateinit var reply_list:MutableList<Reply>
    lateinit var currentFormattedTime :String

    companion object {
        fun newInstance(qid:String):CommentFragment{
            val args=Bundle()
            args.putString("QID",qid)
            val fragment=CommentFragment()
            fragment.arguments=args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val v=inflater.inflate(R.layout.fragment_comment, container, false)

        val commentRecyclerView=v.findViewById<RecyclerView>(R.id.recylerCommentview)
        val editTextMessage=v.findViewById<EditText>(R.id.editTextMessage)
        val send_button=v.findViewById<Button>(R.id.send_button)
        commentRecyclerView.layoutManager= LinearLayoutManager(activity)
        qid= arguments?.getString("QID").toString()
        Log.v("QID",qid)
        var user:User?=null
        var comment:Comment?=null

        comment_list= mutableListOf()
        user_list= mutableListOf()
        reply_list= mutableListOf()

        FirebaseDatabase.getInstance().getReference("/comments/").orderByChild("qid").equalTo(qid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(snapShot in p0.children){
                    comment=snapShot.getValue(Comment::class.java)
                    comment_list.add(comment!!)

                }
            }
        })
        FirebaseDatabase.getInstance().getReference("/users/").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                for(snapShot in p0.children){
                    //Log.v("User TAG",snapShot.child("name").getValue(String::class.java)!!)
                    user=snapShot.getValue(User::class.java)
                    user_list.add(user!!)
                }
            }
        })
        commentRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        var Commentadapter=CommentAdapter(requireContext(),comment_list,user_list,this)
        commentRecyclerView.adapter=Commentadapter

        //Send message button
        send_button.setOnClickListener(){
            val user_id:String= FirebaseAuth.getInstance().uid.toString()
            FirebaseDatabase.getInstance().getReference("/users/").orderByChild("uid").equalTo(user_id).addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        user=p0.getValue(User::class.java)!!
                        if(user!=null){
                            user_list.add(user!!)}
                    }
                }
            })
            var commentMessage:String=editTextMessage.text.toString()
            if(commentMessage.isNotEmpty()) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    val current=LocalDateTime.now()
                    val formatter=DateTimeFormatter.ofPattern("dd.mm.yyyy. HH:mm:ss")
                    currentFormattedTime =current.format(formatter)
                    Log.v("currentFormattedTime",currentFormattedTime)
                }
                else{
                    val date=Date()
                    val formatter=SimpleDateFormat("MM dd yyyy HH:mma")
                    currentFormattedTime =formatter.format(date)
                    Log.v("currentFormattedTime",currentFormattedTime)

                }



                var cid: String = FirebaseDatabase.getInstance().getReference("/comments/").push().key.toString()
                comment = Comment(cid, qid, user_id, commentMessage, currentFormattedTime)
                FirebaseDatabase.getInstance().getReference("/comments/").child(cid)
                    .setValue(comment)
                    .addOnCompleteListener { Log.d("COMMENT ACTIVITY", commentMessage) }
                editTextMessage.text=null
            }
            Toast.makeText(requireContext(),"Comment posted successfully !!!",Toast.LENGTH_SHORT)
            editTextMessage.text=null
        }
        return v
    }

    override fun onClickMessageIconClick(comment: Comment,user:User) {
        var Reply_fragment=ReplyFragment.newInstance(comment,user)
        fragmentManager?.beginTransaction()?.replace(R.id.frameComment,Reply_fragment)?.commit()
        Log.v("COMMENT",comment.comment)
        Log.v("COMMENT",user.name)

    }
}
