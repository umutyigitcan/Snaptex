package com.example.snaptex

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SelectedUserChatSQLite(mContext:Context):SQLiteOpenHelper(mContext,"SelectedUserChat",null,1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE SelectedUserChat(getLoginUser TEXT, selectedUser TEXT)")
        db.execSQL("INSERT INTO SelectedUserChat VALUES('null','null')")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS SelectedUserChat")
    }

}