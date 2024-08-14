package com.example.to_docompose.data.db

import androidx.room.Database
import com.example.to_docompose.data.models.ToDoTaskEntity

@Database(
    entities = [ToDoTaskEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class ToDoTasksDatabase {

    abstract fun toDoTasksDao(): ToDoTasksDao
}
