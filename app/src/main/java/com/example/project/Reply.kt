package com.example.project

import java.io.Serializable

class Reply(var rid:String,var cid: String,var uid:String, var reply_text:String,var rTime:String):Serializable {

    public constructor():this("","","","","")
    {
    }

}
