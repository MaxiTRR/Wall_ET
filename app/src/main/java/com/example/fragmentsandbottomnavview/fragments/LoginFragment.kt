package com.example.fragmentsandbottomnavview.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fragmentsandbottomnavview.HomeActivity
import com.example.fragmentsandbottomnavview.R
import com.example.fragmentsandbottomnavview.RegisterStates
import com.example.fragmentsandbottomnavview.Usuario
import com.example.fragmentsandbottomnavview.databinding.FragmentLoginBinding
import com.google.gson.Gson
import java.lang.Exception


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val preferencias = requireActivity().getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)

        if (validateAutoLogin()){
            goToHomeActivity()
        }

        binding.btnLogin.setOnClickListener {
           // val checkBox = binding.cbRecordar.isChecked
           val edit = preferencias.edit()
           // edit.putBoolean("autoLogin", checkBox)
           // edit.apply()
            val dni = binding.etDniLogin.text.toString().toInt()
            val password = binding.etPasswordLogin.text.toString()

            if (validateData(dni, password) == true) {
                if (binding.cbRecordar.isChecked) {
                    edit.putBoolean("autoLogin", true)
                    edit.apply()
                }
                goToHomeActivity()

            } else {
                edit.putBoolean("autoLogin", false)
                edit.apply()
                Toast.makeText(
                    requireContext(),
                    "DNI o Contraseña Incorrecto",
                    Toast.LENGTH_SHORT
               ).show()
           }

            binding.etDniLogin.text?.clear()
            binding.etPasswordLogin.text?.clear()


        }


        return binding.root
    }


    private fun validateData(dni: Int?, password: String?): Boolean {
        val preferencias = requireActivity().getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
        val usuarioJson = preferencias.getString("usuario", null)

        if (usuarioJson != null) {
            try {
                val gson = Gson()
                val usuario = gson.fromJson(usuarioJson, Usuario::class.java)
                val obtenerDni = usuario.dni
                val obtenerPassword = usuario.password

                if (dni != null && password != null && dni == obtenerDni && password == obtenerPassword) {
                    return true
                }
            } catch (e: Exception) {
                // Manejar errores de deserialización aquí
                e.printStackTrace()
            }
        }

        return false
    }
/*
    private fun nuloOBlanco(dni: Int?, password: String?): Boolean {
        var dni =binding.etDniLogin.text
        var pass =binding.etPasswordLogin.text
        if( dni.isNullOrBlank() && pass.isNullOrBlank()){
            return false
        }else{
            return true
        }
    }
*/
    private fun validateAutoLogin(): Boolean {
        val preferencias = requireActivity().getSharedPreferences(RegisterFragment.CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
        val autoLogin = preferencias.getBoolean("autoLogin", false)
        return autoLogin//false o true
    }
    private fun goToHomeActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }

}