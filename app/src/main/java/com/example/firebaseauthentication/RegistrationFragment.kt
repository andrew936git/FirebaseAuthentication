package com.example.firebaseauthentication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.replace
import com.example.firebaseauthentication.databinding.FragmentRegistrationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        binding.signInBT.setOnClickListener {
            signUpUser()
        }

        binding.iHaveAccountTV.setOnClickListener{
            val transaction = (activity as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.navHostFragment, SignInFragment())
            transaction.addToBackStack(null)
            transaction.setTransition(TRANSIT_FRAGMENT_FADE)
            transaction.commit()

        }
    }



    private fun signUpUser() {
        val email = binding.loginET.text.toString()
        val pass = binding.passwordET.text.toString()
        val confirmPass = binding.confirmPasswordET.text.toString()

        if (email.isBlank() || pass.isBlank() || confirmPass.isBlank()){
            Toast.makeText(
                requireContext(),
                "Адрес электронной почты и пароль не могут быть пустыми",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPass){
            Toast.makeText(
                requireContext(),
                "Пароли не совпадают",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()){
            if (it.isSuccessful){
                Toast.makeText(
                    requireContext(),
                    "Пользователь зарегистрирован",
                    Toast.LENGTH_SHORT).show()
            }else {
                if (auth.currentUser != null){
                    Toast.makeText(
                        requireContext(),
                        "Пользователь уже существует",
                        Toast.LENGTH_SHORT).show()
                    val transaction = (activity as FragmentActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.navHostFragment, SignInFragment())
                    transaction.addToBackStack(null)
                    transaction.setTransition(TRANSIT_FRAGMENT_FADE)
                    transaction.commit()
                }

                Toast.makeText(
                    requireContext(),
                    "Регистрация не прошла",
                    Toast.LENGTH_SHORT).show()
            }


        }
    }
}