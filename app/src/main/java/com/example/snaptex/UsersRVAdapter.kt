package com.example.snaptex

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersRVAdapter(var mContext: Context, var getData: ArrayList<RVAdapterData>) : RecyclerView.Adapter<UsersRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userimg: ImageView
        var username: TextView
        var addfriendbutton: TextView

        init {
            userimg = view.findViewById(R.id.userimg)
            username = view.findViewById(R.id.username)
            addfriendbutton = view.findViewById(R.id.addfriendbutton)
        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {

        var clicked = false
        val myHolder = getData[position]
        holder.username.text = myHolder.username



        val sendedRequest = myHolder.username + "FriendsRequest"
        val sendedRequestDb = FirebaseDatabase.getInstance().getReference(sendedRequest)

        // Giriş yapan kullanıcıyı al
        val vt = SavedUserDatabaseManager(mContext)
        val getLoginUser = SavedUserDatabaseDao().getData(vt)
        val currentUser = getLoginUser.firstOrNull() ?: return

        // Önce butonun durumunu kontrol et
        sendedRequestDb.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var requestAlreadySent = false

                for (p in snapshot.children) {
                    val user = p.getValue(UsersData::class.java)
                    if (user != null && user.username == currentUser.username) {

                        requestAlreadySent = true
                        break
                    }
                }

                if (requestAlreadySent) {

                    holder.addfriendbutton.text="İstek gönderildi"
                    holder.addfriendbutton.isEnabled = false
                } else {

                    holder.addfriendbutton.isEnabled = true

                    holder.addfriendbutton.setOnClickListener {
                        holder.addfriendbutton.text="İstek gönderildi"

                        holder.addfriendbutton.isEnabled = false

                        sendedRequestDb.push().setValue(currentUser)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.addusercardview, parent, false)
        return myCardViewHolder(layout)
    }
}
