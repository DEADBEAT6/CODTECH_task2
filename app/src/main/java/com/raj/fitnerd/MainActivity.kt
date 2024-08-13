package com.raj.fitnerd

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.raj.fitnerd.viewmodel.AuthViewModel
import com.raj.fitnerd.screen.NavigationGraph
import com.raj.fitnerd.ui.theme.FitNerdTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            BackHandler(enabled = true, onBack = {
                if (navController.currentBackStackEntry?.destination?.route == "chatroomscreen") {
                    // Handle back press behavior here (optional)
                } else {
                    navController.popBackStack()
                }
            })
            FitNerdTheme {
                    NavigationGraph(navController =  navController,authViewModel= authViewModel)

            }
        }
    }
}
