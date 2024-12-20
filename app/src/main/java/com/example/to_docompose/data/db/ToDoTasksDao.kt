package com.example.to_docompose.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.to_docompose.data.models.ToDoTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoTasksDao {

    @Query("SELECT * FROM todo_tasks_table WHERE id=:taskId")
    suspend fun getTask(taskId: Int): ToDoTaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: ToDoTaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTasks(tasks: List<ToDoTaskEntity>)

    @Update
    suspend fun updateTask(task: ToDoTaskEntity)

    @Query("DELETE FROM todo_tasks_table WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM todo_tasks_table WHERE id IN (:tasksIds)")
    suspend fun deleteTasks(tasksIds: List<Int>)

    @Query("""
        SELECT * FROM todo_tasks_table WHERE
            title LIKE :searchQuery OR
            description LIKE :searchQuery
    """)
    fun searchTasks(searchQuery: String): Flow<List<ToDoTaskEntity>>
}
