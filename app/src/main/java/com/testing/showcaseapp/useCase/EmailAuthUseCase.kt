package com.testing.showcaseapp.useCase



import com.testing.showcaseapp.repository.AuthState
import com.testing.showcaseapp.repository.SupabaseAuthRepository
import javax.inject.Inject

class EmailAuthUseCase @Inject constructor(
    private val supabaseAuthRepository: SupabaseAuthRepository,
    private val authChecker: AuthChecker
) {

    suspend fun LoginUseCase(email: String, password: String): AuthState {
        val emailCheck = authChecker.validateEmail(email = email)
        if (emailCheck is AuthState.ErrorEmail){
            return emailCheck
        }
        val passwordCheck = authChecker.validatePassword(password = password)
        if (passwordCheck is AuthState.ErrorPassword){
            return passwordCheck
        }
        return supabaseAuthRepository.signInWithEmail(email = email,password = password)
    }

    suspend fun registerUseCase(email: String, password: String,userName:String,confirmPassword:String): AuthState {
        val emailCheck = authChecker.validateEmail(email = email)
        if (emailCheck is AuthState.ErrorEmail){
            return emailCheck
        }
        val passwordCheck = authChecker.validatePassword(password = password)
        if (passwordCheck is AuthState.ErrorPassword){
            return passwordCheck
        }
        if (!authChecker.validateConfirmPassword(password =password, confirmPassword = confirmPassword)){
            return AuthState.ErrorConfirmPassword("the passwords are not the same")
        }
        return supabaseAuthRepository.signUpWithEmail(email = email,password = password, username = userName)
    }

    suspend fun loginVerifyOTP(email:String,token:String):AuthState{
        return supabaseAuthRepository.verifyAndEmailLoginThroughOTP(email = email,token=token)
    }

}