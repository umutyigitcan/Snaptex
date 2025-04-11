package com.example.snaptex

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SavedUserDatabaseManager(mContext:Context):SQLiteOpenHelper(mContext,"SavedUsers",null,1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE SavedUsers(username TEXT,mail TEXT,password TEXT,img INTEGER)")
        db.execSQL("INSERT INTO SavedUsers VALUES('null','null','null',0)")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS SavedUsers")
    }

}