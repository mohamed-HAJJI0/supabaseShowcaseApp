package com.testing.showcaseapp.ui.pages.mainPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.AuthPageRoute
import com.testing.showcaseapp.ui.components.buttons.PrimaryButton
import kotlinx.serialization.Serializable


@Serializable
object MainPageRoute


@Composable
fun MainPage(navController: NavController) {
    Scaffold {paddingValues ->
        Column(
            Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card (
                modifier = Modifier.padding(16.dp)
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ){
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "navigate to pages",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    PrimaryButton(
                        text = "Auth Page",
                        onClick = {navController.navigate(AuthPageRoute)}
                        )

                }
            }
        }
    }
}