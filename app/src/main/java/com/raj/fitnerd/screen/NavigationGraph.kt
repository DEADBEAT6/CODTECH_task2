
package com.raj.fitnerd.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.raj.fitnerd.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,

) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignupScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route)},
                onSignUpSuccess = {navController.navigate(Screen.LoginScreen.route)}
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route){}},
                onLoginSuccess = {navController.navigate(Screen.HomeScreen.route){
                    popUpTo(navController.graph.startDestinationId){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )

        }
        composable(Screen.HomeScreen.route) {
            HomeScreen (
                onCalorieClicked = {navController.navigate(Screen.CalorieScreen.route)},
                onRunClicked = {navController.navigate(Screen.RunsScreen.route)},
                onWorkoutClicked ={navController.navigate(Screen.WorkoutScreen.route)}
            )
        }
        composable("${Screen.ChatScreen.route}/{roomId}/{roomName}") {
            val roomId: String = it.arguments?.getString("roomId") ?: ""
            val roomName: String = it.arguments?.getString("roomName") ?: ""
            ChatScreen(roomId = roomId,roomName = roomName, navigateBack = {
                navController.popBackStack()
            })
        }
        composable(Screen.WorkoutScreen.route){
            WorkoutScreen( onJoinClicked = {navController.navigate("${Screen.ChatScreen.route}/${it.id}/${it.name}")},)
        }
        composable(Screen.RunsScreen.route){
            RunScreen(roomId ="Wrj5BrL1qZ80Be7MEM1G", roomName ="Runs" , navigateBack = { navController.popBackStack()})
        }
        composable(Screen.CalorieScreen.route){
            CalorieScreen(roomId ="QanTrMWSJ4aWTiIGnF6V", roomName ="Calorie" , navigateBack = { navController.popBackStack()})
        }
    }

}