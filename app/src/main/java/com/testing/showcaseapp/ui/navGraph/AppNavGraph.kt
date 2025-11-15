package com.testing.showcaseapp.ui.navGraph

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.AuthPage
import com.ryples.a1_bac._01_FrenchApp._01_pages._07_authPage.AuthPageRoute
import com.testing.showcaseapp.api.supabase
import com.testing.showcaseapp.ui.pages.mainPage.MainPage
import com.testing.showcaseapp.ui.pages.mainPage.MainPageRoute
import com.testing.showcaseapp.ui.pages.successPage.SuccessPage
import com.testing.showcaseapp.ui.pages.successPage.SuccessPageRoute
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(navController : NavHostController){

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    //to collect the auth status and pop it as a toast or you can add any function you want
    LaunchedEffect(Unit) {
        scope.launch {
            supabase.auth.sessionStatus.collect { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        // User is logged in
                        Toast.makeText(context, "User authenticated: ${status.session.user?.email}", Toast.LENGTH_SHORT).show()
                    }
                    is SessionStatus.NotAuthenticated -> {
                        Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
                    }

                    SessionStatus.Initializing -> {
                        Toast.makeText(context, "Initializing", Toast.LENGTH_SHORT).show()

                    }
                    is SessionStatus.RefreshFailure -> {
                        Toast.makeText(context, "Refresh Failure", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    NavHost(
        navController=navController,
        startDestination = MainPageRoute
    ){
        composable <MainPageRoute> {
            MainPage(navController = navController)
        }
        composable <AuthPageRoute>{
            AuthPage(navController = navController)
        }
        composable <SuccessPageRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<SuccessPageRoute>()
            SuccessPage(title = args.title,navController=navController)
        }
    }

}