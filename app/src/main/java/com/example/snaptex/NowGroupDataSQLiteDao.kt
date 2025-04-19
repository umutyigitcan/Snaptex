package com.example.snaptex

class NowGroupDataSQLiteDao {

    fun changeGroupId(vt:NowGroupDataSQLite,groupId:String){

        var db=vt.writableDatabase
        db.execSQL("UPDATE NowGroupData SET groupId='$groupId'")
        db.close()

    }

    fun getData(vt:NowGroupDataSQLite):ArrayList<NowGroupDataClass>{

        var db=vt.writableDatabase
        var cursor=db.rawQuery("SELECT * FROM NowGroupData",null)
        var dataList=ArrayList<NowGroupDataClass>()
        while (cursor.moveToNext()){
            var data=NowGroupDataClass(cursor.getString(cursor.getColumnIndexOrThrow("groupId")))
            dataList.add(data)
        }
        return dataList

    }

}