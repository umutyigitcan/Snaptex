package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterFragment : Fragment() {


    private lateinit var binding:FragmentRegisterBinding
    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)

        binding.registerbutton.setOnClickListener {
            val username = binding.usernameinput.text.toString().trim()
            val mail = binding.mailinput.text.toString().trim()
            val password = binding.passwordinput.text.toString().trim()

            var isValid = true

            // Temizle önceki hataları
            binding.usernameinput.error = null
            binding.mailinput.error = null
            binding.passwordinput.error = null

            if (username.isEmpty()) {
                binding.usernameinput.error = "Kullanıcı adı boş olamaz"
                isValid = false
            } else if (username.length < 8) {
                binding.usernameinput.error = "Kullanıcı adınız en az 8 karakter olmalıdır"
                isValid = false
            }

            if (mail.isEmpty()) {
                binding.mailinput.error = "E-posta boş olamaz"
                isValid = false
            } else {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
                if (!mail.matches(emailPattern.toRegex())) {
                    binding.mailinput.error = "Geçerli bir e-posta adresi giriniz"
                    isValid = false
                }
            }

            if (password.isEmpty()) {
                binding.passwordinput.error = "Şifre boş olamaz"
                isValid = false
            } else if (password.length < 8) {
                binding.passwordinput.error = "Şifreniz en az 8 karakter olmalıdır"
                isValid = false
            }

            if (!isValid) {
                return@setOnClickListener
            }

            
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("Users")

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var usernameExists = false
                    var mailExists = false

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UsersData::class.java)
                        if (user != null) {
                            if (user.username == username) usernameExists = true
                            if (user.mail == mail) mailExists = true
                        }
                    }

                    var dbCheckValid = true

                    if (usernameExists) {
                        binding.usernameinput.error = "Bu kullanıcı adı kullanılmaktadır"
                        dbCheckValid = false
                    }
                    if (mailExists) {
                        binding.mailinput.error = "Bu mail kullanılmaktadır"
                        dbCheckValid = false
                    }

                    if (!dbCheckValid) return


                    val vt = RegisterDatabaseManager(requireContext())
                    RegisterDatabaseDao().userChange(vt, username, mail, password)

                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_registerFragment_to_secondPage)
                }

                override fun onCancelled(error: DatabaseError) {
                    Snackbar.make(requireView(), "Veritabanı hatası oluştu", Snackbar.LENGTH_LONG).show()
                }
            })
        }







        return binding.root
    }


}