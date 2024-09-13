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

    @Query("SELECT * FROM todo_tasks_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTaskEntity>>

    @Query("SELECT * FROM todo_tasks_table WHERE id=:taskId")
    fun getTask(taskId: Int): Flow<ToDoTaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: ToDoTaskEntity)

    @Update
    suspend fun updateTask(task: ToDoTaskEntity)

    @Query("DELETE FROM todo_tasks_table WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM todo_tasks_table")
    suspend fun deleteAllTasks()

    @Query("""
        SELECT * FROM todo_tasks_table WHERE
            title LIKE :searchQuery OR
            description LIKE :searchQuery
    """)
    fun searchTasks(searchQuery: String): Flow<List<ToDoTaskEntity>>

    @Query("SELECT * FROM todo_tasks_table ORDER BY priorityId ASC")
    fun getAllTasksSortedByPriorityAsc(): Flow<List<ToDoTaskEntity>>

    @Query("SELECT * FROM todo_tasks_table ORDER BY priorityId DESC")
    fun getAllTasksSortedByPriorityDesc(): Flow<List<ToDoTaskEntity>>
}
