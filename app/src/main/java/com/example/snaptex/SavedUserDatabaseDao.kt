package com.example.snaptex

class SavedUserDatabaseDao {
    fun userChange(vt:SavedUserDatabaseManager,username:String,mail:String,password:String){
     var db=vt.writableDatabase
        db.execSQL("UPDATE SavedUsers SET username='$username',mail='$mail',password='$password'")
        db.close()
    }

    fun userImgChange(vt:SavedUserDatabaseManager,userimg:Int){
        var db=vt.writableDatabase
        db.execSQL("UPDATE SavedUsers SET img=$userimg")
        db.close()
    }

    fun getData(vt:SavedUserDatabaseManager):ArrayList<RegisterData>{
        var db=vt.writableDatabase
        var list=ArrayList<RegisterData>()
        var cursor=db.rawQuery("SELECT * FROM SavedUsers",null)
        while (cursor.moveToNext()){
            var user=RegisterData(cursor.getString(cursor.getColumnIndexOrThrow("username")),
                cursor.getString(cursor.getColumnIndexOrThrow("mail")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getInt(cursor.getColumnIndexOrThrow("img")))
            list.add(user)
        }
        return list
    }

}