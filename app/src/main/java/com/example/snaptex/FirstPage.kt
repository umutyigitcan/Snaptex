package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentFirstPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirstPage : Fragment() {


    private lateinit var binding:FragmentFirstPageBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        binding= FragmentFirstPageBinding.inflate(inflater,container,false)

        binding.registerbutton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_firstPage_to_registerFragment)
        }




        return binding.root
    }
}