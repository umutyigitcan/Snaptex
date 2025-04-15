package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class UsersRVAdapter(var mContext:Context,var getData:ArrayList<RVAdapterData>):RecyclerView.Adapter<UsersRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var userimg: ImageView
        var username: TextView
        var addfriendbutton: TextView
        init {
            userimg=view.findViewById(R.id.userimg)
            username=view.findViewById(R.id.username)
            addfriendbutton=view.findViewById(R.id.addfriendbutton)
        }

    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        var myHolder=getData[position]
        holder.username.text=myHolder.username

        holder.addfriendbutton.setOnClickListener {nesne->
            holder.addfriendbutton.text="İstek Gönderildi"
            var sendedReuquest=myHolder.username+"FriendsRequest"
            var database=FirebaseDatabase.getInstance()
            var sendedReuquestdb=database.getReference(sendedReuquest)

            var vt=SavedUserDatabaseManager(mContext)
            var getLoginUser=SavedUserDatabaseDao().getData(vt)
            for(k in getLoginUser){
                sendedReuquestdb.push().setValue(UsersData(k.username,k.mail,k.password,k.img))
            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var  layout=LayoutInflater.from(mContext).inflate(R.layout.addusercardview,parent,false)
        return myCardViewHolder(layout)

    }


}