package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentHomePageBinding


class homePage : Fragment() {

    private lateinit var binding:FragmentHomePageBinding
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        binding=FragmentHomePageBinding.inflate(inflater,container,false)

        return binding.root
    }


}