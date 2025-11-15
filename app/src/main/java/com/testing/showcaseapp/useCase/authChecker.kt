package com.testing.showcaseapp.useCase


import com.testing.showcaseapp.repository.AuthState
import javax.inject.Inject

class AuthChecker @Inject constructor(){
    fun validateEmail(email:String): AuthState {
        return when {
            email.isBlank() -> {
                AuthState.ErrorEmail("you should fill this section")
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                AuthState.ErrorEmail("use a valid email")
            }
            else -> AuthState.Loading
        }
    }
    fun validatePassword(password:String): AuthState {
        return when {
            password.isBlank() -> {
                AuthState.ErrorPassword("you should fill this section")

            }
            password.length < 8 -> {
                AuthState.ErrorPassword("the password is too short")
            }
            else -> AuthState.Loading
        }
    }

    fun validateConfirmPassword(password: String,confirmPassword:String): Boolean{
        return password == confirmPassword
    }
}