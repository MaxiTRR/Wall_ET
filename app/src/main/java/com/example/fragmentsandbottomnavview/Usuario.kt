package com.example.fragmentsandbottomnavview

import java.io.Serializable

//Propiedad Account agregada a la clase original, si hay errores borrarlo
data class Usuario(
    val nomApe: String,
    val email: String,
    val dni: Int? = null,
    val tel: Int? = null,
    val password: String,
    val account: Account
): Serializable