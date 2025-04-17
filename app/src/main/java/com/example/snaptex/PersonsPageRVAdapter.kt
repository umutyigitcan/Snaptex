package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class PersonsPageRVAdapter(var mContext:Context,var getData:ArrayList<RVAdapterData>):RecyclerView.Adapter<PersonsPageRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var userimg:ImageView
        var Username:TextView
        var sendMessage:TextView
        init {
            userimg=view.findViewById(R.id.userimggg)
            Username=view.findViewById(R.id.username)
            sendMessage=view.findViewById(R.id.sendMessage)

        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var layout=LayoutInflater.from(mContext).inflate(R.layout.messagepersongpage,parent,false)
        return myCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        var myHolder=getData[position]
        holder.Username.text=myHolder.mail

        holder.sendMessage.setOnClickListener {

            var vt=SelectedUserChatSQLite(mContext)
            var getLoginUser=SelectedUserChatDao().getData(vt)
            for(k in getLoginUser){
                var userLogin=k.getLoginUser
                var selectedUser=myHolder.mail
                var vt2=SelectedUserChatSQLite(mContext)
                SelectedUserChatDao().changeData(vt2,userLogin,selectedUser)
            }

            Navigation.findNavController(it).navigate(R.id.action_homePage_to_chatPage)
        }


    }


}