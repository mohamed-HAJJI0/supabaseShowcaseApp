package com.testing.showcaseapp.ui.pages.successPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.testing.showcaseapp.api.supabase
import com.testing.showcaseapp.ui.pages.mainPage.MainPageRoute
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.Serializable

@Serializable
data class SuccessPageRoute(
    val title:String,
)


@Composable
fun SuccessPage(title:String,navController: NavController) {

    val UserInfo = supabase.auth.currentUserOrNull()


    Scaffold {paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Card(
                modifier = Modifier.padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = {navController.navigate(MainPageRoute)}
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "EMAIL : ${UserInfo?.email}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }
        }
    }
}