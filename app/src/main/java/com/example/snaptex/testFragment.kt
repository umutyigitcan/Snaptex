package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snaptex.databinding.FragmentTestBinding

class testFragment : Fragment() {


    private lateinit var binding:FragmentTestBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding= FragmentTestBinding.inflate(inflater,container,false)


        return binding.root
    }
}