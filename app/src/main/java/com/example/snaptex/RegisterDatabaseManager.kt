package com.example.snaptex

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RegisterDatabaseManager(mContext:Context):SQLiteOpenHelper(mContext,"register",null,1)  {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE register(username TEXT,mail TEXT,password TEXT,img INTEGER)")
        db.execSQL("INSERT INTO register VALUES('null','null','null',0)")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS register")
    }
}