package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentSecondPageBinding
import com.google.firebase.database.FirebaseDatabase

class SecondPage : Fragment() {

    private lateinit var binding:FragmentSecondPageBinding
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,    savedInstanceState: Bundle? ): View? {
       binding=FragmentSecondPageBinding.inflate(inflater,container,false)

        binding.continuebutton.setOnClickListener {

            var vt=RegisterDatabaseManager(requireContext())
            RegisterDatabaseDao().userImgChange(vt,1)
            var database=FirebaseDatabase.getInstance()
            var user=database.getReference("Users")
            var getData=RegisterDatabaseDao().getData(vt)
            for(k in getData){
                var userInfo=UsersData(k.username,k.mail,k.password,k.img)
                user.push().setValue(userInfo)
            }


            Navigation.findNavController(it).navigate(R.id.action_secondPage_to_homePage)
        }


        return binding.root
    }
}