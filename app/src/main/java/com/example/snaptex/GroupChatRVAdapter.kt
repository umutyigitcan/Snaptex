package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupChatRVAdapter(var mContext:Context,var getData:ArrayList<GroupChatRVAdapterData>):RecyclerView.Adapter<GroupChatRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var message:TextView
        var sender:TextView
        init {
            message=view.findViewById(R.id.tv)
            sender=view.findViewById(R.id.userMessage)
        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        val myHolder = getData[position]
        holder.sender.text = myHolder.message
        holder.message.text = myHolder.sender
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var ly=LayoutInflater.from(mContext).inflate(R.layout.messagecardview,parent,false)
        return myCardViewHolder(ly)

    }



}