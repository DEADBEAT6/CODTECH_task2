package com.raj.fitnerd.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raj.fitnerd.data.Injection
import com.raj.fitnerd.data.Result
import com.raj.fitnerd.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class       AuthViewModel : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }
    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> get() = _authResult

    fun signUp(context: Context, email: String, password: String, firstName: String, lastName: String,onSignUpSuccess:()->Unit ) {
        viewModelScope.launch {
            val result = userRepository.signUp(email, password, firstName, lastName)
            _authResult.value = result

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()
                        onSignUpSuccess()

                    }
                    is Result.Error -> {
                        Toast.makeText(context, "Sign up failed: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun login(context: Context, email: String, password: String, onLoginSuccess:()->Unit) {
        viewModelScope.launch {
            val result = userRepository.login(email, password)
            _authResult.value = result

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()


                    }
                    is Result.Error -> {
                        Toast.makeText(context, "Login failed: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}