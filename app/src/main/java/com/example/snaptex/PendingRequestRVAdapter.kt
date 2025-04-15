package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PendingRequestRVAdapter(var mContext:Context,var getData:ArrayList<RVAdapterData>):RecyclerView.Adapter<PendingRequestRVAdapter.myCardViewHolder>() {
    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var userimg: ImageView
        var username: TextView
        var sendMessage: TextView
        init {
            userimg=view.findViewById(R.id.userimg)
            username=view.findViewById(R.id.username)
            sendMessage=view.findViewById(R.id.sendMessage)
        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        var myHolder=getData[position]
        holder.username.text=myHolder.username

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var  layout=LayoutInflater.from(mContext).inflate(R.layout.usercardview,parent,false)
        return myCardViewHolder(layout)

    }
}