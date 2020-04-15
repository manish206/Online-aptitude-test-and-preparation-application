package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowCoins : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_coins)

        val coins=findViewById<TextView>(R.id.coins)
        val userName=findViewById<TextView>(R.id.UserName)
        FirebaseDatabase.getInstance().getReference("/users/").child(FirebaseAuth.getInstance().uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var user=p0.getValue(User::class.java)
                    userName.text=user!!.name.toString()
                }

            })
        FirebaseDatabase.getInstance().getReference("/reward/").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                var x:Int=0
                for(snapshot in p0.children){
                    var reward=snapshot.getValue(Reward::class.java)
                    if(FirebaseAuth.getInstance().uid.toString().equals(reward!!.uid)){
                        x=x+reward!!.coins.toInt()
                    }
                }
                coins.text=x.toString()
            }

        })
    }
}
