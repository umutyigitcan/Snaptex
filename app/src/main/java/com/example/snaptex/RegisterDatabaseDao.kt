package com.example.snaptex

class RegisterDatabaseDao {

   fun  userChange(vt:RegisterDatabaseManager,username:String,mail:String,password:String){
        var db=vt.writableDatabase
        db.execSQL("UPDATE register SET username='$username',mail='$mail',password='$password'")
        db.close()
    }

    fun userImgChange(vt:RegisterDatabaseManager,userimg:Int){
        var db=vt.writableDatabase
        db.execSQL("UPDATE register SET img=$userimg")
        db.close()
    }

    fun getData(vt:RegisterDatabaseManager):ArrayList<RegisterData>{
        var db=vt.writableDatabase
        var list=ArrayList<RegisterData>()

        var cursor=db.rawQuery("SELECT * FROM register",null)
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