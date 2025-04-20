package com.example.snaptex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.databinding.FragmentChatPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class chatPage : Fragment() {

    private lateinit var binding: FragmentChatPageBinding
    private lateinit var adapter: SendedMessageRVAdapter
    private lateinit var dataList: ArrayList<SendedMessageData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatPageBinding.inflate(inflater, container, false)

        dataList = ArrayList()
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.setHasFixedSize(true)

        var userOne = ""
        var userTwo = ""
        var mail = ""
        var password = ""

        var vt = SelectedUserChatSQLite(requireContext())
        var getData = SelectedUserChatDao().getData(vt)
        for (k in getData) {
            var vt3 = SavedUserDatabaseManager(requireContext())
            var getData3 = SavedUserDatabaseDao().getData(vt3)
            for (k in getData3) {
                userOne = k.username
            }
            binding.username.text = k.selectedUser
            userTwo = k.selectedUser
            var vt2 = SavedUserDatabaseManager(requireContext())
            var getData2 = SavedUserDatabaseDao().getData(vt2)
            for (k in getData2) {
                mail = k.mail
                password = k.password
            }


            val database = FirebaseDatabase.getInstance()
            val chatId = listOf(userOne, userTwo).sorted().joinToString("_")
            val sendMessage = database.getReference(chatId)


            sendMessage.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(ds: DataSnapshot) {
                    dataList.clear()
                    for (p in ds.children) {
                        val userlist = p.getValue(SendedMessageData::class.java)
                        if (userlist != null) {
                            val timestamp = p.child("timestamp").getValue(Long::class.java)
                            val formattedDate = if (timestamp != null) {
                                val date = Date(timestamp)
                                val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                                format.format(date)
                            } else {
                                "Unknown Time"
                            }


                            dataList.add(SendedMessageData(userlist.username, userlist.mail, userlist.password, userlist.img, userlist.message, formattedDate))


                            adapter.notifyDataSetChanged()
                            binding.rv.scrollToPosition(dataList.size - 1)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


            binding.send.setOnClickListener {
                val message = binding.input.text.toString()
                if (message.isNotEmpty()) {
                    val messageData = hashMapOf<String, Any>(
                        "username" to userOne,
                        "mail" to mail,
                        "password" to password,
                        "img" to R.drawable.imgshape,
                        "message" to message,
                        "timestamp" to ServerValue.TIMESTAMP
                    )


                    sendMessage.push().setValue(messageData)


                    binding.input.text.clear()
                }
            }
        }


        adapter = SendedMessageRVAdapter(requireContext(), dataList)
        binding.rv.adapter = adapter

        return binding.root
    }
}
