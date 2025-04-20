package com.example.snaptex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentGroupSettingsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GroupSettingsFragment : Fragment() {


    private lateinit var binding:FragmentGroupSettingsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentGroupSettingsBinding.inflate(inflater,container,false)

        binding.button.setOnClickListener {


            if(binding.input.text.isEmpty()){
                binding.input.error="Bu alan boş bırakılamaz"
                return@setOnClickListener
            }


            var vt=GroupDataClassSQLite(requireContext())
            GroupDataClassSQLiteDao().insertGroupNameAndGroupLogo(vt,binding.input.text.toString(),1)

            var database=FirebaseDatabase.getInstance()
            var groupRef=database.getReference("Groups")
            var hash= mutableMapOf<String,Boolean>()
            var getData=GroupDataClassSQLiteDao().getGroupMember(vt)
            var vt2=SavedUserDatabaseManager(requireContext())
            var userLogin=SavedUserDatabaseDao().getData(vt2)


            for(k in getData){
                hash[k.groupMember]=true
                for(p in userLogin){
                 hash[p.username]=true
                }
            }



            groupRef.addListenerForSingleValueEvent(object :ValueEventListener{

                override fun onDataChange(ds: DataSnapshot) {
                    var counter = 1
                    var groupId: String

                    while (true) {
                        groupId = "Group$counter"
                        if (!ds.hasChild(groupId)) {

                            val group = GroupDataClass(binding.input.text.toString(), hash, groupId, 1)
                            groupRef.child(groupId).setValue(group)
                            break
                        }
                        counter++
                    }
                }


                override fun onCancelled(error: DatabaseError) {

                }

            })








           Navigation.findNavController(it).navigate(R.id.action_groupSettingsFragment_to_homePage)
        }

        return binding.root
    }


}