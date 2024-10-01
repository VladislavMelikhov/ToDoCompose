package com.example.to_docompose.data.repositories

import com.example.to_docompose.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoTasksRepository {

    fun getAllTasks(): Flow<List<ToDoTask>>

    fun getTask(taskId: Int): Flow<ToDoTask>

    suspend fun addTask(task: ToDoTask)

    suspend fun updateTask(task: ToDoTask)

    suspend fun deleteTask(taskId: Int)

    suspend fun deleteAllTasks()

    fun searchTasks(searchQuery: String): Flow<List<ToDoTask>>

    fun getAllTasksSortedByPriorityAsc(): Flow<List<ToDoTask>>

    fun getAllTasksSortedByPriorityDesc(): Flow<List<ToDoTask>>
}
