package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snaptex.databinding.FragmentGroupChatPageBinding

class GroupChatPage : Fragment() {

    private lateinit var binding:FragmentGroupChatPageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGroupChatPageBinding.inflate(inflater,container,false)

        var vt=GroupDataClassSQLite(requireContext())
        GroupDataClassSQLiteDao().deleteGroupMembers(vt)



        return binding.root
    }


}