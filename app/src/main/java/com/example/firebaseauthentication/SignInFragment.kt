package com.example.firebaseauthentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.example.firebaseauthentication.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.logInBT.setOnClickListener {
            login()
        }

        binding.redirectTV.setOnClickListener {
            val transaction = (activity as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.navHostFragment, RegistrationFragment())
            transaction.addToBackStack(null)
            transaction.setTransition(TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

    private fun login() {
        val email = binding.loginET.text.toString()
        val pass = binding.passwordET.text.toString()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(requireActivity()){
            if (!it.isSuccessful){
                Toast.makeText(
                    requireContext(),
                    "Не удалось войти в систему",
                    Toast.LENGTH_SHORT).show()
                binding.redirectTV.visibility = View.VISIBLE
                return@addOnCompleteListener
            }
            else{
                val transaction = (activity as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.navHostFragment, ContactFragment())
                transaction.addToBackStack(null)
                transaction.setTransition(TRANSIT_FRAGMENT_FADE)
                transaction.commit()
            }
        }
    }

}