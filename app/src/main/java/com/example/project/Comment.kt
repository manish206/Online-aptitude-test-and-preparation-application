package com.example.project

import java.io.Serializable

public class Comment(var cid:String,var qid:String,var uid:String,var comment:String,var comment_time:String) : Serializable{
    public constructor():this("","","","","")
    {
    }
}