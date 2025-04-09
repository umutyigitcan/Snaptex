package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.snaptex.databinding.FragmentFirstPageBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*

class FirstPage : Fragment() {

    private lateinit var binding: FragmentFirstPageBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstPageBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        userRef = database.getReference("Users")

        binding.registerbutton.setOnClickListener {
            findNavController().navigate(R.id.action_firstPage_to_registerFragment)
        }

        binding.continuebutton.setOnClickListener {
            val email = binding.mailinput.text.toString().trim()
            val password = binding.passwordinput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen e-posta ve şifre girin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(ds: DataSnapshot) {
                    var userFound = false
                    for (p in ds.children) {
                        val userInfo = p.getValue(UsersData::class.java)
                        if (userInfo != null && userInfo.mail == email && userInfo.password == password) {
                            userFound = true
                            findNavController().navigate(R.id.action_firstPage_to_homePage)
                            break
                        }
                    }
                    if (!userFound) {
                        Snackbar.make(binding.root, "Hatalı e-posta veya şifre!", Snackbar.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(ds: DatabaseError) {

                }
            })
        }

        return binding.root
    }
}
