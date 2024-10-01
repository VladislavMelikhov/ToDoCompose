package com.example.to_docompose.data.repositories

import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToDoTasksRepositoryStub @Inject constructor() : ToDoTasksRepository {

    private val tasks: List<ToDoTask> =
        (1..100)
            .map { number ->
                ToDoTask(
                    id = number,
                    title = "Task $number",
                    description = "Description of task $number",
                    priority = Priority.entries[number % Priority.entries.size]
                )
            }

    override fun getAllTasks(): Flow<List<ToDoTask>> =
        flowOf(tasks)

    override fun getTask(taskId: Int): Flow<ToDoTask> =
        flow {
            tasks
                .firstOrNull { task -> task.id == taskId }
                ?.let { task -> emit(task) }
        }

    override suspend fun addTask(task: ToDoTask) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: ToDoTask) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        TODO("Not yet implemented")
    }

    override fun searchTasks(searchQuery: String): Flow<List<ToDoTask>> {
        TODO("Not yet implemented")
    }

    override fun getAllTasksSortedByPriorityAsc(): Flow<List<ToDoTask>> {
        TODO("Not yet implemented")
    }

    override fun getAllTasksSortedByPriorityDesc(): Flow<List<ToDoTask>> {
        TODO("Not yet implemented")
    }
}
