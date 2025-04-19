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
import com.google.firebase.database.ValueEventListener

class GroupChatPage : Fragment() {

    private lateinit var binding: FragmentGroupChatPageBinding
    private lateinit var adapter: GroupChatRVAdapter
    private lateinit var dataList: ArrayList<GroupChatRVAdapterData>

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
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
                    if (message != null) {
                        dataList.add(message)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Mesajlar alınamadı: ${error.message}")
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
            val message = MessageDataClass(sender = getLogin, message = messageText)
            messageRef.push().setValue(message)
            binding.input.setText("")
        }
        adapter = GroupChatRVAdapter(requireContext(), dataList)
        binding.rv.adapter = adapter

        return binding.root
    }
}
