package com.example.to_docompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.to_docompose.data.models.ToDoTaskEntity

@Database(
    entities = [ToDoTaskEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class ToDoTasksDatabase : RoomDatabase() {

    abstract fun toDoTasksDao(): ToDoTasksDao
}
