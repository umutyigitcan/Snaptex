package com.example.snaptex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.snaptex.databinding.FragmentPendingRequestBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingRequest : Fragment() {


    private lateinit var binding:FragmentPendingRequestBinding
    private lateinit var adapter:PendingRequestRVAdapter
    private lateinit var dataList:ArrayList<RVAdapterData>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        binding = FragmentPendingRequestBinding.inflate(layoutInflater)
        dataList=ArrayList()
        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)

        var vt=SavedUserDatabaseManager(requireContext())
        var savedUser=SavedUserDatabaseDao().getData(vt)
        for(k in savedUser){
            var userDB=k.username+"FriendsRequest"
            var database=FirebaseDatabase.getInstance()
            var user=database.getReference(userDB)
            user.addValueEventListener(object :ValueEventListener {
                override fun onDataChange(ds: DataSnapshot) {

                    for(p in ds.children){

                        var user2=p.getValue(UsersData::class.java)
                        if(user2!=null){
                            dataList.add(RVAdapterData(user2.mail!!,user2.username!!,user2.password!!,user2.img!!))
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }








        adapter=PendingRequestRVAdapter(requireContext(),dataList)
        binding.rv.adapter=adapter


        return binding.root
    }


}