package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snaptex.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {


    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)

        var vt=SavedUserDatabaseManager(requireContext())
        var getData=SavedUserDatabaseDao().getData(vt)
        for(k in getData){
            binding.tv.text=k.username
        }


        return binding.root
    }

}