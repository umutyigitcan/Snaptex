package com.example.snaptex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PersonsPageRVAdapter(var mContext:Context,var getData:ArrayList<RVAdapterData>):RecyclerView.Adapter<PersonsPageRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view:View):RecyclerView.ViewHolder(view){
        var userimg:ImageView
        var Username:TextView
        var sendMessage:TextView
        var cl:ConstraintLayout
        var lastMessage:TextView
        init {
            userimg=view.findViewById(R.id.userimggg)
            Username=view.findViewById(R.id.username)
            sendMessage=view.findViewById(R.id.sendMessage)
            cl=view.findViewById(R.id.cl)
            lastMessage=view.findViewById(R.id.lastMessage)

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
        var database=FirebaseDatabase.getInstance()
        var vt=SavedUserDatabaseManager(mContext)
        var getLoginUserDB=SavedUserDatabaseDao().getData(vt)
        var getLoginUser=""
        for(k in getLoginUserDB){
            getLoginUser=k.username
        }
        var lastMessageDB= listOf(getLoginUser,myHolder.mail).sorted().joinToString ("_")
        var lastMessage=database.getReference(lastMessageDB)

        lastMessage.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(ds: DataSnapshot) {
                for(p in ds.children){

                    var lastMessage=p.getValue(SendedMessageData::class.java)
                    if(lastMessage!=null){
                        if(lastMessage.message.length>30) {
                            holder.lastMessage.text = lastMessage.message.take(30).split("\n").lastOrNull().orEmpty() + "..."
                        }else{
                            holder.lastMessage.text=lastMessage.message.split("\n").lastOrNull().orEmpty()
                        }

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        holder.cl.setOnClickListener {
            val vt = SelectedUserChatSQLite(mContext)
            val getLoginUser = SelectedUserChatDao().getData(vt)
            for (k in getLoginUser) {
                val userLogin = k.getLoginUser
                val selectedUser = myHolder.mail
                val vt2 = SelectedUserChatSQLite(mContext)
                SelectedUserChatDao().changeData(vt2, userLogin, selectedUser)
            }


            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_global_chatPage)
            return@setOnClickListener
        }





    }





}