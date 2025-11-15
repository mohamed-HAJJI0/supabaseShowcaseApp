package com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.AuthViewModel
import com.testing.showcaseapp.repository.AuthState
import com.testing.showcaseapp.ui.components.buttons.PrimaryButton
import com.testing.showcaseapp.ui.components.textField.MainPasswordTextField
import com.testing.showcaseapp.ui.components.textField.MainTextField
import com.testing.showcaseapp.ui.theme.ElevationThemeValue
import com.testing.showcaseapp.ui.theme.PaddingThemeValue

@Composable
fun AuthRegisterCard(
    authViewModel: AuthViewModel,
    modifier: Modifier =Modifier,
    navController: NavController
) {

    Column (
        modifier = modifier.fillMaxWidth(),

        ){
        Card(
            modifier = Modifier
                .padding(PaddingThemeValue.MediumPadding),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = ElevationThemeValue.Medium),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //        Text(
                //            text = if (authViewModel.isLoginMode) "Welcome Back" else "Create Account",
                //            style = MaterialTheme.typography.headlineLarge,
                //            modifier = Modifier.padding(bottom = 32.dp)
                //        )

                MainTextField(
                    value = authViewModel.userData.email,
                    onValueChange = { authViewModel.onEmailChange(it) },
                    label = "Email",
                    error = when (authViewModel.authState) {
                        is AuthState.ErrorEmail -> {
                            (authViewModel.authState as AuthState.ErrorEmail).message
                        }

                        else -> ""
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                MainPasswordTextField(
                    value = authViewModel.userData.password,
                    onValueChange = { authViewModel.onPasswordChange(it) },
                    label = "Password",
                    error = when (authViewModel.authState) {
                        is AuthState.ErrorPassword -> {
                            (authViewModel.authState as AuthState.ErrorPassword).message
                        }

                        else -> ""
                    },
                    onPasswordVisibility = {
                        authViewModel.onPasswordVisibility()
                    },
                    passwordVisible = authViewModel.userData.passwordVisibility
                )

                if (!authViewModel.userData.isLoginMode) {
                    Spacer(modifier = Modifier.height(16.dp))

                    MainPasswordTextField(
                        value = authViewModel.userData.confirmedPassword,
                        onValueChange = { authViewModel.onConfirmPasswordChange(it) },
                        label = "Confirm Password",
                        error = when (authViewModel.authState) {
                            is AuthState.ErrorConfirmPassword -> {
                                (authViewModel.authState as AuthState.ErrorConfirmPassword).message
                            }

                            else -> ""
                        },
                        onPasswordVisibility = {
                            authViewModel.onPasswordVisibility()
                        },
                        passwordVisible = authViewModel.userData.passwordVisibility
                    )
                }

                if (authViewModel.authState is AuthState.Error) {
                    Text(
                        text = (authViewModel.authState as AuthState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(PaddingThemeValue.LargePadding)
                    )
                }else{
                    Spacer(modifier = Modifier.padding(PaddingThemeValue.LargePadding))
                }

                PrimaryButton(
                    text = if (authViewModel.userData.isLoginMode) "Login" else "Register",
                    onClick = {
                        if (authViewModel.userData.isLoginMode) {
                            authViewModel.login(navController = navController)
                        } else {
                            authViewModel.register()
                        }
                    },
                    isLoading = authViewModel.authState is AuthState.Loading
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    authViewModel.onLoginMode()
                }) {
                    Text(
                        text = if (authViewModel.userData.isLoginMode)
                            "Don't have an account? Register"
                        else
                            "Already have an account? Login"
                    )
                }


            }
        }
    }
}