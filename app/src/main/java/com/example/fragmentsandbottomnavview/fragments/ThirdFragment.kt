package com.example.fragmentsandbottomnavview.fragments

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
import com.example.fragmentsandbottomnavview.databinding.FragmentFirstBinding
import com.example.fragmentsandbottomnavview.databinding.FragmentThirdBinding
import com.google.gson.Gson

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)

        binding.btnRetirar.setOnClickListener {
            var monto = binding.etRetirarText.text.toString().toFloat()
            retirarDinero(monto)
            binding.etRetirarText.text?.clear()
            goToHomeFragment()
        }
        return binding.root
    }

    private fun retirarDinero(monto:Float?) {
        val preferencias = requireActivity().getSharedPreferences(
            RegisterFragment.CREDENTIALS,
            AppCompatActivity.MODE_PRIVATE
        )
        val cuentaJson = preferencias.getString("usuario", null)
        val gson = Gson()
        var usuario = gson.fromJson(cuentaJson, Usuario::class.java)
        // var cantEnCuenta = usuario.account.ammount
        if (monto != null) {
            if (monto > 0) {
                if (usuario.account.ammount < monto) {
                    Toast.makeText(
                        requireContext(),
                        "El monto a Retirar es Mayor al Disponible",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    usuario.account.ammount -= monto

                    val accountInJsonFormat = gson.toJson(usuario)
                    val edit = preferencias.edit()
                    edit.putString("usuario", accountInJsonFormat)
                    edit.apply()

                    Toast.makeText(
                        requireContext(),
                        "Retiro de $ ${monto} Realizado!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Debe ingresar un valor Mayor a 0",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

   private fun goToHomeFragment(){
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
    }
}