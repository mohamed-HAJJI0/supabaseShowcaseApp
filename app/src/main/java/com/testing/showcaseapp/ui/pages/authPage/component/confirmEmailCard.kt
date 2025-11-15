package com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.AuthViewModel
import com.testing.showcaseapp.repository.AuthState
import com.testing.showcaseapp.ui.components.buttons.PrimaryButton
import com.testing.showcaseapp.ui.components.textField.MainTextField
import com.testing.showcaseapp.ui.theme.ElevationThemeValue
import com.testing.showcaseapp.ui.theme.PaddingThemeValue

@Composable
fun ConfirmEmailCard(modifier: Modifier = Modifier, authViewModel: AuthViewModel,navController: NavController) {
    Column (
        modifier = modifier.fillMaxWidth(),
        ){
        Card(
            modifier = Modifier
                .padding(PaddingThemeValue.MediumPadding)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = ElevationThemeValue.Medium),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(PaddingThemeValue.MediumPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "We’ve sent a 6-digit code to:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Text(
                    text = authViewModel.userData.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                MainTextField(
                    value = authViewModel.confirmValue,
                    onValueChange = {
                        if (it.length <= 6 && it.all { c -> c.isDigit() }) {
                            authViewModel.onConfirmValue(it)
                        }
                    },
                    label = "Enter 6-digit code"
                )
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
                    onClick = {
                        authViewModel.onConfirmAction(navController = navController)
                    },
                    text = "Confirm Email",
                    desabled = !authViewModel.confirmValueIsValid,
                    isLoading = authViewModel.authState is AuthState.Loading

                )

            }
        }
    }
}

@Preview
@Composable
fun ConfirmEmailCardPreveiw(modifier: Modifier = Modifier) {
    ConfirmEmailCard(authViewModel = viewModel(), navController = rememberNavController())
}