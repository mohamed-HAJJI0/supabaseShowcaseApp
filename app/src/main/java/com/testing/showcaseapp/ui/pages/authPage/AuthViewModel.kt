package com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.testing.showcaseapp.repository.AuthState
import com.testing.showcaseapp.useCase.EmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



data class AuthData(
    var email:String="",
    var password:String="",
    var userName:String="akram",
    var confirmedPassword:String ="",
    var passwordVisibility: Boolean=false,
    var isLoginMode: Boolean = true
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val emailAuthUseCase: EmailAuthUseCase
) : ViewModel() {

    var authState : AuthState by mutableStateOf(AuthState.DataInserting)
        private set
    var userData:AuthData by mutableStateOf(AuthData())
        private set

    var confirmValue by mutableStateOf("")
        private set
    var confirmValueIsValid by mutableStateOf(false)
        private set
    var isConfirmingPage by mutableStateOf(false)
        private set

    fun popBack (){
        isConfirmingPage = !isConfirmingPage
        authState = AuthState.DataInserting
        userData = AuthData()

    }
    fun onConfirmValue(value:String){
        confirmValue = value
        confirmValueIsValid = confirmValue.length == 6
    }

    fun onLoginMode (){
        authState = AuthState.DataInserting
        userData = userData.copy(isLoginMode = !userData.isLoginMode)
    }
    fun onPasswordVisibility(){
        userData = userData.copy(passwordVisibility = !userData.passwordVisibility)
    }

    fun onEmailChange(newEmail: String) {
        authState = AuthState.DataInserting
        userData = userData.copy(email = newEmail)

    }



    fun onPasswordChange(newPassword: String) {
        authState = AuthState.DataInserting
        userData = userData.copy(password = newPassword)

    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        authState = AuthState.DataInserting
        userData = userData.copy(confirmedPassword = newConfirmPassword)
    }



    fun login(navController: NavController) {
        authState = AuthState.Loading
        viewModelScope.launch {
            authState = emailAuthUseCase.LoginUseCase(
                email = userData.email,
                password = userData.password
            )
            if (authState == AuthState.Success){
//                navController.navigate(MainPageRoute)
            }
        }
    }

    fun register() {
        authState = AuthState.Loading
        viewModelScope.launch {
            authState =emailAuthUseCase.registerUseCase(
                email = userData.email,
                password = userData.password,
                userName = userData.userName,
                confirmPassword = userData.confirmedPassword
            )
            if (authState is AuthState.Success){
                isConfirmingPage = true
            }
        }
    }
    fun onConfirmAction(navController: NavController){
        authState = AuthState.Loading
        viewModelScope.launch {
            authState = emailAuthUseCase.loginVerifyOTP(
                email = userData.email,
                token = confirmValue
            )
        }
        if (authState == AuthState.Success){
//            navController.navigate(MainPageRoute)
        }
    }


}
