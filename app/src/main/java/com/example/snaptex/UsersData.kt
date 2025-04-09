package com.example.snaptex

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UsersData(var username:String?=null,var mail:String?=null,var password:String?=null,var img:Int?=null) {
}