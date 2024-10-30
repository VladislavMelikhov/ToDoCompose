package com.example.to_docompose.data.repositories

import com.example.to_docompose.data.models.TasksSortPolicy
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val sortPolicy: Flow<TasksSortPolicy>

    suspend fun saveSortPolicy(sortPolicy: TasksSortPolicy)
}
