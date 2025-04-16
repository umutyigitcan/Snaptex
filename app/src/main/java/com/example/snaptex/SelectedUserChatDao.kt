package com.example.snaptex

class SelectedUserChatDao {

    fun changeData(vt:SelectedUserChatSQLite,getLoginUser:String,selectedUser:String){
        val db=vt.writableDatabase
        db.execSQL("UPDATE SelectedUserChat SET getLoginUser='$getLoginUser',selectedUser='$selectedUser'")
        db.close()
    }

    fun getData(vt:SelectedUserChatSQLite):ArrayList<SelectedUserChatData>{
        val db=vt.writableDatabase
        var data=ArrayList<SelectedUserChatData>()
        val cursor=db.rawQuery("SELECT * FROM SelectedUserChat",null)
        if(cursor.moveToNext()){
            var getData=SelectedUserChatData(cursor.getString(cursor.getColumnIndexOrThrow("getLoginUser")),
                cursor.getString(cursor.getColumnIndexOrThrow("selectedUser")))
            data.add(getData)
        }
        return data
    }

}