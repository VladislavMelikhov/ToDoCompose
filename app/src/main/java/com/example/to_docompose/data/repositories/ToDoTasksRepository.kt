package com.example.to_docompose.data.repositories

import com.example.to_docompose.data.db.ToDoTasksDao
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.models.ToDoTaskEntity
import com.example.to_docompose.data.models.toDomain
import com.example.to_docompose.data.models.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ToDoTasksRepository @Inject constructor(
    private val toDoTasksDao: ToDoTasksDao,
) {

    fun getAllTasks(): Flow<List<ToDoTask>> =
        flowOf(
            (1..0)
                .map { number ->
                    ToDoTask(
                        id = number,
                        title = "Task $number",
                        description = "Description of task $number",
                        priority = Priority.entries[number % Priority.entries.size]
                    )
                }
        )
//        toDoTasksDao.getAllTasks()
//            .map(List<ToDoTaskEntity>::toDomain)

    fun getTask(taskId: Int): Flow<ToDoTask> =
        toDoTasksDao.getTask(taskId)
            .map(ToDoTaskEntity::toDomain)

    suspend fun addTask(task: ToDoTask) {
        val taskEntity = task.toEntity()
        toDoTasksDao.addTask(taskEntity)
    }

    suspend fun updateTask(task: ToDoTask) {
        val taskEntity = task.toEntity()
        toDoTasksDao.updateTask(taskEntity)
    }

    suspend fun deleteTask(taskId: Int) {
        toDoTasksDao.deleteTask(taskId)
    }

    suspend fun deleteAllTasks() {
        toDoTasksDao.deleteAllTasks()
    }

    fun searchTasks(searchQuery: String): Flow<List<ToDoTask>> =
        toDoTasksDao.searchTasks(searchQuery)
            .map(List<ToDoTaskEntity>::toDomain)

    fun getAllTasksSortedByPriorityAsc(): Flow<List<ToDoTask>> =
        toDoTasksDao.getAllTasksSortedByPriorityAsc()
            .map(List<ToDoTaskEntity>::toDomain)

    fun getAllTasksSortedByPriorityDesc(): Flow<List<ToDoTask>> =
        toDoTasksDao.getAllTasksSortedByPriorityDesc()
            .map(List<ToDoTaskEntity>::toDomain)
}
