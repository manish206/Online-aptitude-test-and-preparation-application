package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class View_total_users : AppCompatActivity() {
lateinit var user_list:MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_total_users)
        user_list= mutableListOf()
        val recyclerUserView=findViewById<RecyclerView>(R.id.recycler_view_user)
        recyclerUserView.layoutManager=LinearLayoutManager(this)

        FirebaseDatabase.getInstance().getReference("/users/")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        for(snapShot in p0.children){
                            var app_user= snapShot.getValue(User::class.java)!!
                            Log.v("UID",app_user.uid)
                            user_list.add(app_user)
                        }
                    }
                    val adapter=UserAdapter(this@View_total_users,ArrayList(user_list))

                    recyclerUserView.adapter=adapter
                }
            })
    }
}
