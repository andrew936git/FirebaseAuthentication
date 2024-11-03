package com.example.firebaseauthentication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseauthentication.databinding.FragmentContactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    private val list = mutableListOf<ContactData?>()
    private lateinit var  adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBT.setOnClickListener{
            val transaction = (activity as FragmentActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.navHostFragment, SignInFragment())
            transaction.addToBackStack(null)
            transaction.setTransition(TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }

        binding.exitBT.setOnClickListener {
            requireActivity().finishAffinity()
        }

        val id = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.database.reference.child("contacts")
            .child(id).get().addOnSuccessListener {
                for (element in it.children){
                    val contact = element.getValue(ContactData::class.java)!!
                    list.add(contact)
                }

            binding.contactListRV.layoutManager = LinearLayoutManager(requireContext())
            adapter = CustomAdapter(list)
            binding.contactListRV.adapter = adapter

            adapter.notifyDataSetChanged()
        }

        binding.saveBT.setOnClickListener{
            val name = binding.nameET.text.toString()
            val phone = binding.phoneET.text.toString()

            if (name.isBlank() || phone.isBlank()) return@setOnClickListener
            val contact = ContactData(name, phone)
            addContact(contact)
            list.add(contact)
            adapter.notifyDataSetChanged()
            with(binding){
                nameET.text.clear()
                phoneET.text.clear()
            }
        }
    }

    private fun addContact(contactAdd: ContactData) {
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val database = Firebase.database.reference
            .child("contacts")
            .child(id)

        val map = HashMap<String, ContactData>()
        map[contactAdd.name.toString()] = contactAdd
        database.updateChildren(map as Map<String, Any>)
    }


}