package com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.component.AuthRegisterCard
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.component.ConfirmEmailCard
import kotlinx.serialization.Serializable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.testing.showcaseapp.ui.components.topBar.PopBackTopBar

@Composable
fun AuthPage(
    authViewModel: AuthViewModel = hiltViewModel(),  // ✅ Correct!
    navController: NavController
) {




    BackHandler {
        if (authViewModel.isConfirmingPage){
            authViewModel.popBack()
        }else{
            navController.popBackStack()
        }
    }

    Scaffold (
        topBar = {PopBackTopBar(
            onPopBack = {
                navController.popBackStack()
            }
        )
        }
    ){Padding->
        Column (Modifier.padding(Padding)){

            if(authViewModel.isConfirmingPage){
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Confirm Your Email",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Column (
                        modifier = Modifier.padding(bottom = 64.dp)
                    ) {
                        ConfirmEmailCard(
                            authViewModel = authViewModel,
                            modifier =  Modifier.fillMaxWidth(),
                            navController=navController
                        )
                    }
                }
            }else{
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = if (authViewModel.userData.isLoginMode) "Welcome Back" else "Create Account",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Column (
                        modifier = Modifier.padding(bottom = 64.dp)
                    ){
                        AuthRegisterCard(
                            authViewModel = authViewModel,
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}




@Serializable
object AuthPageRoute
