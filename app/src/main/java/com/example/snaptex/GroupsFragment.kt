package com.example.snaptex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.databinding.FragmentGroupsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class GroupsFragment : Fragment() {

    private lateinit var binding:FragmentGroupsBinding
    private lateinit var addGroupAdapter:addUsertoGroupsRVAdapter
    private lateinit var groupList:ArrayList<addUsertoGroupsData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGroupsBinding.inflate(inflater,container,false)

       /* var database2 = FirebaseDatabase.getInstance()
        var groupRef = database2.getReference("GroupsTest")

        val members = mapOf(
            "Ahmet" to true,
            "Mehmet" to true,
            "Ali" to true
        )

        val groupData = GroupData("Grup1", members)
        groupRef.child("Group1").setValue(groupData)*/


        var database=FirebaseDatabase.getInstance()
        binding.rv.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.rv.setHasFixedSize(true)
        groupList=ArrayList()

        var vt=SavedUserDatabaseManager(requireContext())
        var getData=SavedUserDatabaseDao().getData(vt)
        var loginUser=""
        for(k in getData){
            loginUser=k.username+"Friends"
        }
        var loginUserDB=database.getReference(loginUser)

        loginUserDB.addListenerForSingleValueEvent(object :ValueEventListener{

            override fun onDataChange(ds: DataSnapshot) {
                groupList.clear()
                for(p in ds.children){
                    var userList=p.getValue(addUsertoGroupsData::class.java)

                    if(userList!=null){
                        groupList.add(addUsertoGroupsData(userList.username,userList.mail,userList.password,userList.logo))
                    }


                }
                addGroupAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.button.setOnClickListener {

            var vt=GroupDataClassSQLite(requireContext())
            var getData=GroupDataClassSQLiteDao().getGroupMember(vt)
            for (k in getData){
                Log.e("DENEME",k.groupMember)
            }

            Navigation.findNavController(it).navigate(R.id.action_groupsFragment_to_groupSettingsFragment)
        }






        addGroupAdapter= addUsertoGroupsRVAdapter(requireContext(),groupList)
        binding.rv.adapter=addGroupAdapter





        return binding.root
    }
}