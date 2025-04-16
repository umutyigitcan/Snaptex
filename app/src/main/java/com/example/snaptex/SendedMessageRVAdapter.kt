package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SendedMessageRVAdapter(var mContext:Context,var getData:ArrayList<SendedMessageData>):RecyclerView.Adapter<SendedMessageRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var tv:TextView
        var userMessage:TextView
        init {
            tv=view.findViewById(R.id.tv)
            userMessage=view.findViewById(R.id.userMessage)
        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        var myHolder=getData[position]
        holder.tv.text=myHolder.username
        holder.userMessage.text=myHolder.message
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var layout=LayoutInflater.from(mContext).inflate(R.layout.messagecardview,parent,false)
        return myCardViewHolder(layout)

    }


}