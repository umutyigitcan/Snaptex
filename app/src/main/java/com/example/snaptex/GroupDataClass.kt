package com.example.snaptex

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GroupDataClass(var groupName:String?=null,var groupMembers:Map<String,Boolean>?=null,var groupId:String?="",var groupLogo:Int?=1) {
}