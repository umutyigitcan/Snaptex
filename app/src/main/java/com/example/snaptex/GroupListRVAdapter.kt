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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GroupListRVAdapter(var mContext: Context, var getData: ArrayList<GroupListRVAdapterData>) :
    RecyclerView.Adapter<GroupListRVAdapter.myCardViewHolder>() {

    inner class myCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var groupLogo: ImageView
        var groupName: TextView
        var groupSendMessage: TextView
        var cl: ConstraintLayout
        var lastMessage: TextView
        var time: TextView

        init {
            cl = view.findViewById(R.id.cl)
            groupLogo = view.findViewById(R.id.groupLogo)
            groupName = view.findViewById(R.id.groupName)
            groupSendMessage = view.findViewById(R.id.groupSendMessage)
            lastMessage = view.findViewById(R.id.lastMessage)
            time = view.findViewById(R.id.time)
        }
    }

    override fun getItemCount(): Int {
        return getData.size
    }

    override fun onBindViewHolder(holder: myCardViewHolder, position: Int) {
        val myHolder = getData[position]

        holder.groupName.text = myHolder.groupName

        val database = FirebaseDatabase.getInstance()
        val groupRef = database.getReference("Groups")

        groupRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                for (p in ds.children) {
                    val groupData = p.getValue(GroupDataClass::class.java)
                    if (groupData != null && groupData.groupId == myHolder.groupId) {
                        val data = groupData.groupMessage
                        if (data != null) {
                            // Son mesajı ve timestamp değerini çekiyoruz
                            var lastMessageText = "Henüz mesaj yok"
                            var lastTimestamp: Long = 0

                            for ((key, message) in data) {
                                if (message.timestamp > lastTimestamp) {
                                    lastMessageText = message.message
                                    lastTimestamp = message.timestamp
                                }
                            }

                            holder.lastMessage.text = lastMessageText

                            // Zamanı formatlamak için
                            val formattedTime = formatTimestamp(lastTimestamp)
                            holder.time.text = formattedTime
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumu
            }
        })

        holder.cl.setOnClickListener {
            val vt = NowGroupDataSQLite(mContext)
            NowGroupDataSQLiteDao().changeGroupId(vt, myHolder.groupId.toString())
            Navigation.findNavController(it).navigate(R.id.action_homePage_to_groupChatPage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myCardViewHolder {
        val ly = LayoutInflater.from(mContext).inflate(R.layout.grouplistcardview, parent, false)
        return myCardViewHolder(ly)
    }

    private fun formatTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
