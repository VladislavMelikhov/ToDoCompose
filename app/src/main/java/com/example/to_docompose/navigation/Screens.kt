package com.example.to_docompose.navigation

import androidx.navigation.NavHostController

class Screens(navController: NavHostController) {

    val list: (Int) -> Unit = { actionId ->
        navController.navigate(route = "list/$actionId") {
            popUpTo("list") { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
}
