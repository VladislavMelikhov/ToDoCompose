package com.example.to_docompose.data.repositories

import com.example.to_docompose.data.db.ToDoTasksDao
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.models.ToDoTaskEntity
import com.example.to_docompose.data.models.toDomain
import com.example.to_docompose.data.models.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoTasksRepositoryImpl @Inject constructor(
    private val toDoTasksDao: ToDoTasksDao,
) : ToDoTasksRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> =
        toDoTasksDao.getAllTasks()
            .map(List<ToDoTaskEntity>::toDomain)

    override suspend fun getTask(taskId: Int): ToDoTask =
        toDoTasksDao.getTask(taskId)
            .let(ToDoTaskEntity::toDomain)

    override suspend fun addTask(task: ToDoTask) {
        val taskEntity = task.toEntity()
        toDoTasksDao.addTask(taskEntity)
    }

    override suspend fun addTasks(tasks: List<ToDoTask>) {
        val taskEntities = tasks.map(ToDoTask::toEntity)
        toDoTasksDao.addTasks(taskEntities)
    }

    override suspend fun updateTask(task: ToDoTask) {
        val taskEntity = task.toEntity()
        toDoTasksDao.updateTask(taskEntity)
    }

    override suspend fun deleteTask(taskId: Int) {
        toDoTasksDao.deleteTask(taskId)
    }

    override suspend fun deleteTasks(tasksIds: List<Int>) {
        toDoTasksDao.deleteTasks(tasksIds)
    }

    override fun searchTasks(searchQuery: String): Flow<List<ToDoTask>> =
        toDoTasksDao.searchTasks("%$searchQuery%")
            .map(List<ToDoTaskEntity>::toDomain)

    override fun getAllTasksSortedByPriorityAsc(): Flow<List<ToDoTask>> =
        toDoTasksDao.getAllTasksSortedByPriorityAsc()
            .map(List<ToDoTaskEntity>::toDomain)

    override fun getAllTasksSortedByPriorityDesc(): Flow<List<ToDoTask>> =
        toDoTasksDao.getAllTasksSortedByPriorityDesc()
            .map(List<ToDoTaskEntity>::toDomain)
}
