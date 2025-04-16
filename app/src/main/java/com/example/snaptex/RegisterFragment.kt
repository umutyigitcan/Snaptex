package com.example.snaptex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.snaptex.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {


    private lateinit var binding:FragmentRegisterBinding
    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)

        binding.registerbutton.setOnClickListener {
            if (binding.usernameinput.text.isEmpty()) {
                Snackbar.make(requireView(), "Kullanıcı adı boş olamaz", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.mailinput.text.isEmpty()) {
                Snackbar.make(requireView(), "E-posta boş olamaz", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.passwordinput.text.isEmpty()) {
                Snackbar.make(requireView(), "Şifre boş olamaz", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.usernameinput.text.toString().length < 8) {
                Snackbar.make(requireView(), "Kullanıcı adınız en az 8 karakter olmalıdır", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.passwordinput.text.toString().length < 8) {
                Snackbar.make(requireView(), "Şifreniz en az 8 karakter olmalıdır", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val email = binding.mailinput.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
            if (!email.matches(emailPattern.toRegex())) {
                Snackbar.make(requireView(), "Geçerli bir e-posta adresi giriniz", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Veritabanına kaydetme işlemi
            val vt = RegisterDatabaseManager(requireContext())
            val username = binding.usernameinput.text.toString()
            val mail = binding.mailinput.text.toString()
            val password = binding.passwordinput.text.toString()
            RegisterDatabaseDao().userChange(vt, username, mail, password)

            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_secondPage)
        }





        return binding.root
    }


}