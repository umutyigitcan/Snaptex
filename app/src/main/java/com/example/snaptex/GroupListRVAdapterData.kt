package com.example.snaptex

data class GroupListRVAdapterData(var groupName:String?=null,var groupId:String?=null,var groupLogo:Int?=null) {
}
data class GroupMessageClass(
    val message: String = "",
    val sender: String = "",
    val timestamp: Long = 0L
)

