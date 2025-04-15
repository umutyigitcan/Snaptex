package com.example.snaptex

import AddPersonPage
import android.os.Bundle
import android.provider.ContactsContract.Groups
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.snaptex.databinding.FragmentHomePageBinding
import com.google.android.material.tabs.TabLayoutMediator


class homePage : Fragment() {

    private lateinit var binding:FragmentHomePageBinding
    private lateinit var fragmentList:ArrayList<Fragment>
    private lateinit var fragmentTitleList:ArrayList<String>
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        binding=FragmentHomePageBinding.inflate(inflater,container,false)
        fragmentTitleList=ArrayList()
        fragmentList=ArrayList()
        fragmentList.add(PersonsPage())
        fragmentList.add(GroupsFragment())
        fragmentTitleList.add("Persons")
        fragmentTitleList.add("Groups")

        binding.addFriend.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homePage_to_addPersonPage)
        }

        val adapter=myViewPagerAdapter(requireActivity())
        binding.vp.adapter=adapter
        TabLayoutMediator(binding.tb,binding.vp){tab,position->
            tab.setText(fragmentTitleList[position])
        }.attach()

        binding.pendingRequest.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homePage_to_pendingRequest2)
        }

        return binding.root
    }

    inner class myViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]

        }

        override fun getItemCount(): Int {
            return fragmentList.size
        }
    }


}