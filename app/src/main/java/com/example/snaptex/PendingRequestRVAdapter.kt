package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class PendingRequestRVAdapter(var mContext:Context,var getData:ArrayList<RVAdapterData>):RecyclerView.Adapter<PendingRequestRVAdapter.myCardViewHolder>() {
    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var userimg: ImageView
        var username: TextView
        var acceptRequest: TextView
        init {
            userimg=view.findViewById(R.id.userimg)
            username=view.findViewById(R.id.username)
            acceptRequest=view.findViewById(R.id.sendMessage)
        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        var myHolder=getData[position]
        holder.username.text=myHolder.username

        holder.acceptRequest.setOnClickListener {
            var database=FirebaseDatabase.getInstance()
            var vt=SavedUserDatabaseManager(mContext)
            var getLoginUser=SavedUserDatabaseDao().getData(vt)
            for(k in getLoginUser){
             var userLogin=k.username+"Friends"
             var saveFriends=database.getReference(userLogin)
             saveFriends.push().setValue(UsersData(myHolder.username,myHolder.mail,myHolder.password,myHolder.img))
                var selectedUser=myHolder.username+"Friends"
                var selectedUserdb=database.getReference(selectedUser)
                selectedUserdb.push().setValue(UsersData(k.username,k.mail,k.password,k.img))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var  layout=LayoutInflater.from(mContext).inflate(R.layout.usercardview,parent,false)
        return myCardViewHolder(layout)

    }
}