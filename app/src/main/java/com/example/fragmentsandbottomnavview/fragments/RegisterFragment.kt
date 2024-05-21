package com.example.fragmentsandbottomnavview.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.fragmentsandbottomnavview.Account
import com.example.fragmentsandbottomnavview.MainActivity
import com.example.fragmentsandbottomnavview.RegisterStates
import com.example.fragmentsandbottomnavview.RegisterViewModel
import com.example.fragmentsandbottomnavview.Usuario
import com.example.fragmentsandbottomnavview.databinding.FragmentRegisterBinding
import com.google.gson.Gson


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        viewModel.viewState.observe(viewLifecycleOwner) {
            handleViewState(it)
        }

        binding.etDni.addTextChangedListener {
            viewModel.validateDni(it.toString().toInt())
        }

        binding.etTel.addTextChangedListener {
            viewModel.validateTel(it.toString().toInt())
        }

        binding.etNomApe.addTextChangedListener {
            viewModel.validateNomApe(it.toString())
        }
        binding.etEmail.addTextChangedListener {
            viewModel.validateEmail(it.toString())
        }

        binding.etPassword.addTextChangedListener {
            viewModel.validatePass(it.toString())
        }

        binding.etConfirmPass.addTextChangedListener {
            viewModel.validateConfirmPass(it.toString())
        }

        binding.btnRegister.setOnClickListener {
            val nomApe = binding.etNomApe.text.toString()
            val email = binding.etEmail.text.toString()
            val dni = binding.etDni.text.toString().toInt()
            val tel = binding.etTel.text.toString().toInt()
            val password= binding.etPassword.text.toString()
            val confirmPass = binding.etConfirmPass.text.toString()
            //Instancia de la data class ACCOUNT
            val account = Account(ammount = 0f)
            //Se agrego la propiedad account (en cso de error, borarrlo y eleminarlo de la data class Persona)
            val usuario = Usuario(nomApe,email,dni, tel,password, account)

            val preferencias = requireActivity().getSharedPreferences(CREDENTIALS, AppCompatActivity.MODE_PRIVATE)
            val edit = preferencias.edit()

            val gson = Gson()
            //Codigo para editar el archivo Account de SharedPreferences
            val accountInJsonFormat = gson.toJson(account)
            edit.putString("cuenta", accountInJsonFormat)

            //Codigo para editar el archivo de Usuario de SharedPreferences
            val usuarioInJsonFormat = gson.toJson(usuario)
            edit.putString("usuario", usuarioInJsonFormat)
            edit.apply()

            Toast.makeText(requireContext(), "Usuario Creado!", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
        const val CREDENTIALS = "Credenciales"
    }

    private fun handleViewState(viewState: RegisterStates) {
        when (viewState) {
            is RegisterStates.SuccessTelField -> binding.layoutTel.error = null

            is RegisterStates.ErrorTelField -> binding.layoutTel.error = viewState.tel

            is RegisterStates.SuccessDNIField -> binding.layoutDni.error = null

            is RegisterStates.ErrorDNIField -> binding.layoutDni.error = viewState.dni

            is RegisterStates.SuccessNomApeField -> binding.layoutNomApe.error = null

            is RegisterStates.ErrorNomApeField -> binding.layoutNomApe.error = viewState.nomApe

            is RegisterStates.SuccessEmailField -> binding.layoutEmail.error = null

            is RegisterStates.ErrorEmailField -> binding.layoutEmail.error = viewState.email

            is RegisterStates.SuccessPassField -> binding.layoutPassword.error = null

            is RegisterStates.ErrorPassField -> binding.layoutPassword.error = "Mínimo: ${viewState.password.length}/4"

            is RegisterStates.SuccessRepeatPassField -> binding.layoutConfirmPass.error = null

            is RegisterStates.ErrorRepeatPassField -> binding.layoutConfirmPass.error = viewState.message

            is RegisterStates.BtnSuccess -> binding.btnRegister.isEnabled = true

            is RegisterStates.BtnError -> binding.btnRegister.isEnabled = false
        }
    }
}