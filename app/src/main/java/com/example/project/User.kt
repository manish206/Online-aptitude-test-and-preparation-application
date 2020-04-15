package com.example.project

import java.io.Serializable

public class User(var uid: String,var name: String,var device_token:String) : Serializable {
    public constructor():this("","",""){

    }
}