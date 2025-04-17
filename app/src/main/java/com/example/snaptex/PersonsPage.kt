package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.databinding.FragmentPersonsPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PersonsPage : Fragment() {


    private lateinit var binding:FragmentPersonsPageBinding
    private lateinit var dataList:ArrayList<RVAdapterData>
    private lateinit var adapter:PersonsPageRVAdapter



    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentPersonsPageBinding.inflate(inflater,container,false)

        dataList=ArrayList()
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)

        var database=FirebaseDatabase.getInstance()
        var vt=SavedUserDatabaseManager(requireContext())
        var getLoginUser=SavedUserDatabaseDao().getData(vt)
        for(k in getLoginUser){
            var list=database.getReference(k.username+"Friends")
            list.addValueEventListener(object:ValueEventListener{
                override fun onDataChange(ds: DataSnapshot) {
                    dataList.clear()
                    for(k in ds.children){
                        var users=k.getValue(UsersData::class.java)
                        if(users!=null){
                            dataList.add(RVAdapterData(users.username.toString(),users.mail.toString(),users.password.toString(),users.img!!))
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        adapter=PersonsPageRVAdapter(requireContext(),dataList)
        binding.rv.adapter=adapter
        return binding.root
    }
}