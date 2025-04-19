package com.example.snaptex

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GroupDataClassSQLite(mContext:Context):SQLiteOpenHelper(mContext,"GroupData",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE GroupData(groupName TEXT,groupId TEXT,groupLogo INTEGER)")
        db.execSQL("INSERT INTO GroupData VALUES('null','null',1)")
        db.execSQL("CREATE TABLE GroupMembers(groupMembers TEXT)")

    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

    }
}