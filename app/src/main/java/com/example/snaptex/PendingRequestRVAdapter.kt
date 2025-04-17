package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
            val database = FirebaseDatabase.getInstance()
            val vt = SavedUserDatabaseManager(mContext)
            val getLoginUser = SavedUserDatabaseDao().getData(vt)

            for (k in getLoginUser) {
                val userLogin = k.username + "Friends"
                val saveFriends = database.getReference(userLogin)
                saveFriends.push().setValue(UsersData(myHolder.username, myHolder.mail, myHolder.password, myHolder.img))

                val selectedUser = myHolder.username + "Friends"
                val selectedUserdb = database.getReference(selectedUser)
                selectedUserdb.push().setValue(UsersData(k.username, k.mail, k.password, k.img))

                val userLogin2 = k.username + "FriendsRequest"
                val userLogin2db = database.getReference(userLogin2)

                val selectedUserDB = myHolder.username + "FriendsRequest"
                val selectedUserDB2 = database.getReference(selectedUserDB)


                userLogin2db.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(ds: DataSnapshot) {
                        for (p in ds.children) {
                            val user = p.getValue(UsersData::class.java)
                            if (user != null && user.username == myHolder.username) {
                                userLogin2db.child(p.key!!).removeValue()


                                getData.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, getData.size)
                                Snackbar.make(holder.itemView, "Arkadaşlık isteği kabul edildi", Snackbar.LENGTH_LONG).show()
                                break
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })


                selectedUserDB2.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(ds: DataSnapshot) {
                        for (p in ds.children) {
                            val user = p.getValue(UsersData::class.java)
                            if (user != null && user.username == k.username) {
                                selectedUserDB2.child(p.key!!).removeValue()
                                break
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        var  layout=LayoutInflater.from(mContext).inflate(R.layout.usercardview,parent,false)
        return myCardViewHolder(layout)

    }
}