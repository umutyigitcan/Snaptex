package com.example.snaptex

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NowGroupDataSQLite(mContext:Context):SQLiteOpenHelper(mContext,"NowGroupData",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE NowGroupData(groupId TEXT)")
        db.execSQL("INSERT INTO NowGroupData VALUES('null')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}