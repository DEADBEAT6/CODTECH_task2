package com.raj.fitnerd.screen

sealed class Screen(val route:String){
    object LoginScreen: Screen("loginscreen")
    object SignupScreen: Screen("signupscreen")
    object HomeScreen: Screen("homescreen")
    object ChatScreen: Screen("chatscreen")

    object WorkoutScreen: Screen("workoutscreen")
    object RunsScreen: Screen("runscreen")
    object CalorieScreen: Screen("caloriescreen")

}