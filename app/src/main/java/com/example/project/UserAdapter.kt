package com.example.project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    lateinit var user_list: ArrayList<User>
    lateinit var context: Context
    constructor(context: Context, user_list:ArrayList<User>):this(){
        this.user_list=user_list
        this.context=context
    }
    class UserViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name:TextView=itemView.findViewById(R.id.txtViewname)
        //val email:TextView=itemView.findViewById(R.id.txtViewEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view:View=inflater.inflate(R.layout.view_users,parent,false)

        return UserAdapter.UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user_list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var U:User=user_list.get(position)
        holder.name.text=U.name.toString()
        if(U.uid.toString()!=null){
            var EMAIL:String=U.uid
        }
    }
}