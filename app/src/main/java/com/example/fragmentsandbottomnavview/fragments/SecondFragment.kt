package com.example.fragmentsandbottomnavview.fragments

import android.accounts.Account
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentsandbottomnavview.HomeActivity
import com.example.fragmentsandbottomnavview.R
import com.example.fragmentsandbottomnavview.Usuario
import com.example.fragmentsandbottomnavview.databinding.FragmentSecondBinding
import com.example.fragmentsandbottomnavview.fragments.FirstFragment.Companion.ACCOUNT
import com.google.gson.Gson

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.btnDeposit.setOnClickListener {
            var monto = binding.etDepositText.text.toString().toFloat()
            ingresarDinero(monto)
            binding.etDepositText.text?.clear()
            goToHomeFragment()
        }


        return binding.root
    }


   private fun ingresarDinero(monto:Float?){
        val preferencias = requireActivity().getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
        val cuentaJson = preferencias.getString("usuario", null)
        val gson = Gson()
        var usuario = gson.fromJson(cuentaJson, Usuario::class.java)
       // var cantEnCuenta = usuario.account.ammount
        if (monto != null) {
            if (monto > 0){

                usuario.account.ammount += monto

                val accountInJsonFormat = gson.toJson(usuario)
                val edit = preferencias.edit()
                edit.putString("usuario", accountInJsonFormat)
                edit.apply()

                Toast.makeText(requireContext(), "Depósito de $ ${monto} Realizado!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Debe ingresar un valor Mayor a 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun goToHomeFragment(){
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
    }
}