package com.example.fragmentsandbottomnavview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentsandbottomnavview.R
import com.example.fragmentsandbottomnavview.Usuario
import com.example.fragmentsandbottomnavview.databinding.FragmentFirstBinding
import com.google.gson.Gson

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)

        val preferencias = requireActivity().getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
        val jsonOfPerson = preferencias.getString("usuario","")

        val gson = Gson()
        val usuario = gson.fromJson(jsonOfPerson, Usuario::class.java)


        binding.userName.text = usuario.nomApe
        binding.tvAmmount.text ="$ ${usuario.account.ammount.toString()}"


        return binding.root
    }

    //Creando el object que da nombre al archivo ACCOUNT de las sharedPreferences
    companion object {
        const val ACCOUNT = "Cuenta"
    }
}