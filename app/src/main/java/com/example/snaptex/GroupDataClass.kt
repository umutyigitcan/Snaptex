package com.example.snaptex

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class GroupDataClass(
    var groupName: String? = null,
    var groupMembers: Map<String, Boolean>? = null,
    var groupId: String? = "",
    var groupLogo: Int? = 1,
    var groupMessage: Map<String, MessageDataClass>? = null
)

data class MessageDataClass(
    var sender: String = "",
    var message: String = ""
)
