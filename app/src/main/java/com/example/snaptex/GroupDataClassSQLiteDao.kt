package com.example.snaptex

class GroupDataClassSQLiteDao {


    fun insertGroupNameAndGroupLogo(vt:GroupDataClassSQLite,groupName:String,groupLogo:Int){

        var db=vt.writableDatabase
        db.execSQL("UPDATE GroupData SET groupName='$groupName',groupLogo=$groupLogo ")
        db.close()

    }

    fun insertGroupId(vt:GroupDataClassSQLite,groupId:String){

        var db=vt.writableDatabase
        db.execSQL("UPDATE GroupData SET groupId='$groupId' ")
        db.close()

    }

    fun getData(vt:GroupDataClassSQLite):ArrayList<GroupDataClassNow>{

        var db=vt.writableDatabase
        var cursor=db.rawQuery("SELECT * FROM GroupData",null)
        var groupList=ArrayList<GroupDataClassNow>()
        while(cursor.moveToNext()){
            var group=GroupDataClassNow(cursor.getString(cursor.getColumnIndexOrThrow("groupName")),
                cursor.getString(cursor.getColumnIndexOrThrow("groupId")),
                cursor.getInt(cursor.getColumnIndexOrThrow("groupLogo")))
            groupList.add(group)
        }
        return groupList
    }




    /* --- */

    fun insertGroupMembers(vt:GroupDataClassSQLite,groupMembers:String){

        var db=vt.writableDatabase
        db.execSQL("INSERT INTO GroupMembers VALUES('$groupMembers')")
        db.close()

    }

    fun deleteGroupMembers(vt:GroupDataClassSQLite){

        var db=vt.writableDatabase
        db.execSQL("DELETE FROM GroupMembers")
        db.close()

    }

    fun deleteGroupMember(vt:GroupDataClassSQLite,deleteMember:String){

        var db=vt.writableDatabase
        db.execSQL("DELETE FROM GroupMembers WHERE groupMembers='$deleteMember'")
        db.close()

    }

    fun getGroupMember(vt:GroupDataClassSQLite):ArrayList<GroupMembersData>{

        var db=vt.writableDatabase
        var cursor=db.rawQuery("SELECT * FROM GroupMembers",null)
        var groupMemberList=ArrayList<GroupMembersData>()
        while(cursor.moveToNext()){
            var users=GroupMembersData(cursor.getString(cursor.getColumnIndexOrThrow("groupMembers")))
            groupMemberList.add(users)
        }
        return groupMemberList

    }



}