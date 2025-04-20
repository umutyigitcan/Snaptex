package com.example.snaptex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.databinding.FragmentGroupChatPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GroupChatPage : Fragment() {

    private lateinit var binding: FragmentGroupChatPageBinding
    private lateinit var adapter: GroupChatRVAdapter
    private lateinit var dataList: ArrayList<GroupChatRVAdapterData>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGroupChatPageBinding.inflate(inflater, container, false)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        dataList = ArrayList()

        val vt2 = NowGroupDataSQLite(requireContext())
        val getGroupID = NowGroupDataSQLiteDao().getData(vt2)
        var groupId = ""
        for (k in getGroupID) {
            groupId = k.groupId.toString()
        }

        val database = FirebaseDatabase.getInstance()
        val groupRef = database.getReference("Groups")

        groupRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                for (k in ds.children) {
                    val group = k.getValue(GroupDataClass::class.java)
                    val groupIdDB = k.key
                    if (group != null && groupIdDB == groupId) {
                        binding.username.text = group.groupName
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        val messageRef = groupRef.child(groupId).child("groupMessage")
        messageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(GroupChatRVAdapterData::class.java)
                    val timestamp = messageSnapshot.child("timestamp").getValue(Long::class.java)

                    val formattedDate = if (timestamp != null) {
                        val date = Date(timestamp)
                        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                        format.format(date)
                    } else {
                        "Unkown Time"
                    }

                    if (message != null) {
                        val newMessage = GroupChatRVAdapterData(
                            sender = message.sender,
                            message = message.message,
                            time = formattedDate
                        )
                        dataList.add(newMessage)
                    }
                }
                adapter.notifyDataSetChanged()
                binding.rv.scrollToPosition(dataList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        binding.send.setOnClickListener {
            val vt3 = SavedUserDatabaseManager(requireContext())
            val getLoginDB = SavedUserDatabaseDao().getData(vt3)
            var getLogin = ""
            for (k in getLoginDB) {
                getLogin = k.username
            }

            val messageText = binding.input.text.toString()
            val messageData = hashMapOf<String, Any>(
                "sender" to getLogin,
                "message" to messageText,
                "timestamp" to ServerValue.TIMESTAMP
            )
            messageRef.push().setValue(messageData)
            binding.input.setText("")
        }

        adapter = GroupChatRVAdapter(requireContext(), dataList)
        binding.rv.adapter = adapter

        return binding.root
    }
}
