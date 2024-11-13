package com.example.to_docompose.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Splash : Screen

    @Serializable
    data object TasksList : Screen

    @Serializable
    data class TaskDetails(val taskId: Int) : Screen
}
