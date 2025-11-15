package com.testing.showcaseapp.repository

import com.testing.showcaseapp.api.supabase
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

sealed class AuthState {
    object Loading : AuthState()
    object Success : AuthState()
    object DataInserting: AuthState()
    data class Error(val message: String) : AuthState()
    data class ErrorEmail(val message:String): AuthState()
    data class ErrorPassword(val message:String): AuthState()
    data class ErrorConfirmPassword(val message: String): AuthState()
}



class SupabaseAuthRepository @Inject constructor(
//    private val supabase: SupabaseClient
) {
    suspend fun signInWithEmail(email: String, password: String): AuthState {
        return withContext(Dispatchers.IO) {
            try {
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                AuthState.Success

            } catch (e: Exception) {
                AuthState.Error(AuthErrorMapper.mapError(e))
            }
        }
    }

    suspend fun signUpWithEmail(email: String, password: String, username: String): AuthState {
        return withContext(Dispatchers.IO) {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                    data = buildJsonObject {
                        put("first_name", username)
                    }                }
                AuthState.Success

            } catch (e: Exception) {
                AuthState.Error(AuthErrorMapper.mapError(e))
            }
        }
    }

    suspend fun verifyAndEmailLoginThroughOTP(email:String,token:String):AuthState{
        return withContext(Dispatchers.IO){
            try {
                supabase.auth.verifyEmailOtp(
                    type = OtpType.Email.EMAIL,
                    email = email,
                    token = token
                )
                AuthState.Success

            } catch (e: Exception) {
                AuthState.Error(AuthErrorMapper.mapError(e))
            }
        }
    }
}

object AuthErrorMapper {
    fun mapError(exception: Exception): String {
        return when {
            // ========== AUTHENTICATION ERRORS ==========

            // Invalid credentials
            exception.message?.contains("Invalid login credentials", ignoreCase = true) == true ->
                "The email or password you entered is incorrect."

            exception.message?.contains("Invalid email or password", ignoreCase = true) == true ->
                "Please check your email and password."

            // Email errors
            exception.message?.contains("Email not confirmed", ignoreCase = true) == true ->
                "Please check your email and verify your account."

            exception.message?.contains("Invalid email", ignoreCase = true) == true ->
                "Please enter a valid email address."

            exception.message?.contains("User already registered", ignoreCase = true) == true ->
                "An account with this email already exists."

            exception.message?.contains("Signups not allowed", ignoreCase = true) == true ->
                "New sign-ups are currently disabled."

            // Password errors
            exception.message?.contains("Password should be at least", ignoreCase = true) == true ->
                "Password must be at least 6 characters long."

            exception.message?.contains("Password is too weak", ignoreCase = true) == true ->
                "Please choose a stronger password."

            // OTP/Token errors
            exception.message?.contains("Token has expired", ignoreCase = true) == true ->
                "Verification code has expired. Please request a new one."

            exception.message?.contains("Invalid token", ignoreCase = true) == true ->
                "Invalid verification code. Please try again."

            exception.message?.contains("OTP expired", ignoreCase = true) == true ->
                "Verification code expired. Request a new one."

            // Session errors
            exception.message?.contains("Session not found", ignoreCase = true) == true ->
                "Your session has expired. Please sign in again."

            exception.message?.contains("Refresh token not found", ignoreCase = true) == true ->
                "Session invalid. Please sign in again."

            exception.message?.contains("JWT expired", ignoreCase = true) == true ->
                "Your session has expired. Please log in again."

            // Rate limiting
            exception.message?.contains("Email rate limit exceeded", ignoreCase = true) == true ->
                "Too many emails sent. Please try again later."

            exception.message?.contains("Too many requests", ignoreCase = true) == true ->
                "Too many attempts. Please wait a moment."

            exception.message?.contains("Rate limit", ignoreCase = true) == true ->
                "You're doing that too often. Please slow down."

            // User errors
            exception.message?.contains("User not found", ignoreCase = true) == true ->
                "No account found with this email."

            exception.message?.contains("User banned", ignoreCase = true) == true ->
                "Your account has been suspended. Contact support."

            // ========== NETWORK ERRORS ==========

            exception is java.net.UnknownHostException ->
                "No internet connection available."

            exception is java.net.SocketTimeoutException ->
                "Connection timed out. Please try again."

            exception is java.net.ConnectException ->
                "Cannot connect to server. Check your internet."

            exception is java.io.IOException ->
                "Network error occurred. Please check your connection."

            // ========== SERVER ERRORS ==========

            exception.message?.contains("500", ignoreCase = true) == true ->
                "Server error. Please try again later."

            exception.message?.contains("502", ignoreCase = true) == true ->
                "Service temporarily unavailable."

            exception.message?.contains("503", ignoreCase = true) == true ->
                "Service maintenance. Try again shortly."

            exception.message?.contains("504", ignoreCase = true) == true ->
                "Server timeout. Please try again."

            exception.message?.contains("Database", ignoreCase = true) == true ->
                "Database error. Please try again later."
            // Default
            else -> "Something went wrong. Please try again later."
        }
    }
}

