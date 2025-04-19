package com.example.snaptex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.databinding.FragmentGroupListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GroupListFragment : Fragment() {


    private lateinit var binding:FragmentGroupListBinding
    private lateinit var adapter:GroupListRVAdapter
    private lateinit var dataList:ArrayList<GroupListRVAdapterData>
    override fun onCreateView(   inflater: LayoutInflater, container: ViewGroup?,   savedInstanceState: Bundle?  ): View? {
        binding= FragmentGroupListBinding.inflate(inflater,container,false)
        dataList=ArrayList()
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)

        var database=FirebaseDatabase.getInstance()
        var groupData=database.getReference("Groups")
        groupData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (p in snapshot.children) {
                    val groupData = p.getValue(GroupDataClass::class.java)
                    if (groupData != null) {
                        val groupName = groupData.groupName
                        val members = groupData.groupMembers


                        if(members!=null){
                            for ((username, isMember) in members) {
                                if (isMember) {
                                    var vt=SavedUserDatabaseManager(requireContext())
                                    var getLoginUser=SavedUserDatabaseDao().getData(vt)
                                    for(k in getLoginUser){
                                        if(k.username==username){
                                            dataList.add(GroupListRVAdapterData(groupName,groupData.groupId,1))
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Veri alınamadı: ${error.message}")
            }
        })




        adapter= GroupListRVAdapter(requireContext(),dataList)
        binding.rv.adapter=adapter
        return binding.root
    }


}