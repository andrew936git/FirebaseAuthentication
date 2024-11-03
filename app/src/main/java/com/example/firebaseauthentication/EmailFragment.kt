package com.example.firebaseauthentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseauthentication.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {

    private var _binding: FragmentEmailBinding? = null
    private val binding get() = _binding!!

    private val list = listOf(
        "Отправитель1:   заголовок письма1",
        "Отправитель2:   заголовок письма2",
        "Отправитель3:   заголовок письма3",
        "Отправитель4:   заголовок письма4",
        "Отправитель5:   заголовок письма5",
        "Отправитель6:   заголовок письма6",
        "Отправитель7:   заголовок письма7",
        "Отправитель8:   заголовок письма8",
        "Отправитель9:   заголовок письма9",
        "Отправитель10:   заголовок письма10",
        "Отправитель11:   заголовок письма11",
        "Отправитель12:   заголовок письма12",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mailListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.mailListRV.adapter = CustomAdapter(list)
    }


}