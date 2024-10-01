package com.example.to_docompose.navigation

import androidx.navigation.NavHostController

class Screens(navController: NavHostController) {

    // TODO: clear back stack properly
    val list: (ToDoTaskAction) -> Unit = { action ->
        navController.navigate(route = "list/${action.id}") {
            popUpTo("list") { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route = "task/$taskId")
    }
}
