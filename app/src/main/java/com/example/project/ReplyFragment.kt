package com.example.project

import android.content.Context
import android.media.TimedText
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size
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

class ReplyFragment : Fragment() {
    lateinit var comment:Comment
    private lateinit var user:User
    lateinit var user_list:MutableList<User>
    lateinit var reply_list:MutableList<Reply>
    lateinit var currentFormattedTime:String
    companion object{
        fun newInstance(comment: Comment,user: User) : ReplyFragment{
            val args=Bundle()
            args.putSerializable("COMMENT",comment)
            args.putSerializable("USER",user)
            val fragment=ReplyFragment()
            fragment.arguments=args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val v=inflater.inflate(R.layout.fragment_reply, container, false)
        val txtViewName=v.findViewById<TextView>(R.id.txtViewName)
        val txtViewTime=v.findViewById<TextView>(R.id.txtViewTime)
        val txtViewComment=v.findViewById<TextView>(R.id.txtViewCommentMessage)
        val sendReplyButton=v.findViewById<Button>(R.id.sendReplyButton)
        val editTextReply=v.findViewById<EditText>(R.id.editTextReply)
        val recyclerReplyView=v.findViewById<RecyclerView>(R.id.recylerReplyView)
        val cutImageView=v.findViewById<ImageView>(R.id.cutImageView)
        var currentUserId:String=FirebaseAuth.getInstance().currentUser?.uid.toString()
        user_list= mutableListOf()
        reply_list= mutableListOf()


        comment=arguments!!.getSerializable("COMMENT") as Comment
        user=arguments!!.getSerializable("USER") as User
        Log.v("COMMENT",comment.comment)
        Log.v("USER",user.name)
        txtViewName.text=user.name
        txtViewTime.text=comment.comment_time.toString()
        txtViewComment.text=comment.comment
        var i:Int="0".toInt()

        FirebaseDatabase.getInstance().getReference("/reply/").orderByChild("cid").equalTo(comment.cid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(snapShot in p0.children){
                    var reply=snapShot.getValue(Reply::class.java)
                    reply_list.add(reply!!)
                    i=i+"1".toInt()
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
                    var user_reply=snapShot.getValue(User::class.java)
                    user_list.add(user_reply!!)
                }
            }
        })
        recyclerReplyView.layoutManager= LinearLayoutManager(requireContext())
        var replyAdapter=ReplyAdapter(requireContext(),reply_list,user_list,comment)
        recyclerReplyView.adapter=replyAdapter


        sendReplyButton.setOnClickListener(View.OnClickListener {
            val user_id:String= FirebaseAuth.getInstance().uid.toString()
            FirebaseDatabase.getInstance().getReference("/users/").orderByChild("uid").equalTo(user_id).addValueEventListener(object :
                ValueEventListener {
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
            var commentMessage:String=editTextReply.text.toString()
            if(editTextReply.text.toString().isNotEmpty()) {
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    val current= LocalDateTime.now()
                    val formatter= DateTimeFormatter.ofPattern("dd.mm.yyyy. HH:mm:ss")
                    currentFormattedTime =current.format(formatter)
                    Log.v("currentFormattedTime",currentFormattedTime)
                }
                else{
                    val date= Date()
                    val formatter= SimpleDateFormat("MM dd yyyy HH:mma")
                    currentFormattedTime =formatter.format(date)
                    Log.v("currentFormattedTime",currentFormattedTime)

                }
                var rid: String = FirebaseDatabase.getInstance().getReference("/reply/").push().key.toString()
                var reply :Reply= Reply(rid,comment.cid,currentUserId, editTextReply.text.toString(), currentFormattedTime.toString())
                FirebaseDatabase.getInstance().getReference("/reply/").child(rid)
                    .setValue(reply)
                    .addOnCompleteListener { Log.d("REPLY FRAGMENT", commentMessage) }

                Toast.makeText(requireContext(),"Comment posted successfully !!!", Toast.LENGTH_SHORT)
                editTextReply.text=null
            }
            clear(recyclerReplyView)
        })
        cutImageView.setOnClickListener(View.OnClickListener {

            var comment_fragment=CommentFragment.newInstance(comment.qid)
            fragmentManager?.beginTransaction()?.replace(R.id.frameComment,comment_fragment)?.commit()
        })


        return v
    }
    fun clear( recyclerReplyView:RecyclerView){
        recyclerReplyView.recycledViewPool.clear()
            }
}
