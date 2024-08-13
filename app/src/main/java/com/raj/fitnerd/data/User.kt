package com.raj.fitnerd.data

data class User(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val type: String ?= ""

)

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

