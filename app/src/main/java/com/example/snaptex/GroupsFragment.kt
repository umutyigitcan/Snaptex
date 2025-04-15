package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snaptex.databinding.FragmentGroupsBinding


class GroupsFragment : Fragment() {

    private lateinit var binding:FragmentGroupsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentGroupsBinding.inflate(inflater,container,false)



        return binding.root
    }
}