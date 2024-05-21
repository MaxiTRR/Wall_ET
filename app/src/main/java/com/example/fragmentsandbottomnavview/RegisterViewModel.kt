package com.example.fragmentsandbottomnavview

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class RegisterViewModel: ViewModel() {




    private val _viewState = MutableLiveData<RegisterStates>()
    val viewState: LiveData<RegisterStates> get() = _viewState

    private var nomApe: String = ""
    private var email: String = ""
    private var dni: Int? = null
    private var tel: Int? = null
    private var password: String = ""
    private var confirmPass: String = ""


    private fun validateButtons() {
        if (nomApe.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()  && dni.toString().isNotEmpty() && tel.toString().isNotEmpty() && (password == confirmPass && password.isNotEmpty() && confirmPass.isNotEmpty())) {
            _viewState.value = RegisterStates.BtnSuccess
        } else {
            _viewState.value = RegisterStates.BtnError
        }
    }

    fun validateNomApe(nomApe: String) {
        if (nomApe.isNotEmpty() && nomApe.length >= 5 && nomApe.contains(" ")) {
            _viewState.value = RegisterStates.SuccessNomApeField
            this.nomApe = nomApe
        } else {
            _viewState.value = RegisterStates.ErrorNomApeField(nomApe = "Ingrese Nombre y Apellido válido")
        }
        validateButtons()
    }

    fun validateEmail(email: String) {
        if (email.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            _viewState.value = RegisterStates.SuccessEmailField
            this.email = email
        } else {
            _viewState.value = RegisterStates.ErrorEmailField(email = "Ingrese un Email válido")
        }
        validateButtons()
    }

    fun validateDni(dni: Int) {
        val dniString = dni.toString()
        if (dniString.length >= 7 && dniString.length <= 8) {
            val dniInt = dniString.toInt() // Convertir de nuevo a Int
            _viewState.value = RegisterStates.SuccessDNIField
            this.dni = dniInt
        } else {
            _viewState.value = RegisterStates.ErrorDNIField("Ingrese un número de DNI válido")
        }
        validateButtons()
    }

    fun validateTel(tel: Int) {
        val telString = tel.toString()
        if (telString.length >= 8 && telString.length <= 14) {
            val telInt = telString.toInt() // Convertir de nuevo a Int
            _viewState.value = RegisterStates.SuccessTelField
            this.tel = telInt
        } else {
            _viewState.value = RegisterStates.ErrorTelField("Ingrese un número de Teléfono válido")
        }
        validateButtons()
    }

    fun validatePass(password: String) {
        if (password.isNotEmpty() && password.length >= 4) {
            _viewState.value = RegisterStates.SuccessPassField
            this.password = password
        } else {
            _viewState.value = RegisterStates.ErrorPassField(password = password)
        }
        validateButtons()
    }


    fun validateConfirmPass(repeatPass: String) {
        if (password == repeatPass) {
            _viewState.value = RegisterStates.SuccessRepeatPassField
            this.confirmPass = repeatPass
        } else {
            _viewState.value = RegisterStates.ErrorRepeatPassField(message = "Las contraseñas no coinciden")
        }
        validateButtons()
    }


}


sealed class RegisterStates() {
    object SuccessTelField: RegisterStates()
    data class ErrorTelField(val tel: String): RegisterStates()
    object SuccessDNIField: RegisterStates()
    data class ErrorDNIField(val dni: String): RegisterStates()
    object SuccessNomApeField: RegisterStates()
    data class ErrorNomApeField(val nomApe: String): RegisterStates()
    object SuccessEmailField: RegisterStates()
    data class ErrorEmailField(val email: String): RegisterStates()
    object SuccessPassField: RegisterStates()
    data class ErrorPassField(val password: String): RegisterStates()
    object SuccessRepeatPassField: RegisterStates()
    data class ErrorRepeatPassField(val message: String): RegisterStates()
    object BtnSuccess: RegisterStates()
    object BtnError: RegisterStates()
}