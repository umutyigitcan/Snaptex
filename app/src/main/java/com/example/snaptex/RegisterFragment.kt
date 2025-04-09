package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentRegisterBinding
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {


    private lateinit var binding:FragmentRegisterBinding
    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)

        binding.registerbutton.setOnClickListener {
            val vt=RegisterDatabaseManager(requireContext())
            var username=binding.usernameinput.text.toString()
            var mail=binding.mailinput.text.toString()
            var password=binding.passwordinput.text.toString()
            RegisterDatabaseDao().userChange(vt,username,mail,password)
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_secondPage)
        }




        return binding.root
    }


}