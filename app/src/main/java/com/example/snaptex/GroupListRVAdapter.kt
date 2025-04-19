package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class GroupListRVAdapter(var mContext:Context,var getData:ArrayList<GroupListRVAdapterData>):RecyclerView.Adapter<GroupListRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var groupLogo:ImageView
        var groupName:TextView
        var groupSendMessage:TextView
        init {
            groupLogo=view.findViewById(R.id.groupLogo)
            groupName=view.findViewById(R.id.groupName)
            groupSendMessage=view.findViewById(R.id.groupSendMessage)
        }

    }
    override fun getItemCount(): Int {
        return getData.size
    }
    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        val myHolder = getData[position]

        holder.groupName.text = myHolder.groupName

        holder.groupSendMessage.setOnClickListener {

            var vt=NowGroupDataSQLite(mContext)
            NowGroupDataSQLiteDao().changeGroupId(vt,myHolder.groupId.toString())

            Navigation.findNavController(it).navigate(R.id.action_homePage_to_groupChatPage)
        }




    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var ly=LayoutInflater.from(mContext).inflate(R.layout.grouplistcardview,parent,false)
        return myCardViewHolder(ly)
    }


}