package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class addUsertoGroupsRVAdapter(var mContext:Context,var dataList:ArrayList<addUsertoGroupsData>):RecyclerView.Adapter<addUsertoGroupsRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var userimg:ImageView
        var username:TextView
        var checkbox:CheckBox
        init {
            userimg=view.findViewById(R.id.userimg)
            username=view.findViewById(R.id.username)
            checkbox=view.findViewById(R.id.checkbox)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        val myHolder = dataList[position]

        holder.username.text = myHolder.username
        holder.userimg.setImageResource(R.drawable.logo)


        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = myHolder.isChecked


        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            myHolder.isChecked = isChecked

            val vt = GroupDataClassSQLite(mContext)

            if (isChecked) {
                GroupDataClassSQLiteDao().insertGroupMembers(vt, myHolder.username)
            } else {
                GroupDataClassSQLiteDao().deleteGroupMember(vt, myHolder.username)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var ly=LayoutInflater.from(mContext).inflate(R.layout.addusertogroupcardview,parent,false)
        return myCardViewHolder(ly)


    }

}