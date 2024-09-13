package com.example.to_docompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.to_docompose.data.db.DbConstants

@Entity(tableName = DbConstants.TASKS_TABLE_NAME)
data class ToDoTaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priorityId: Int,
)

fun ToDoTaskEntity.toDomain(): ToDoTask =
    ToDoTask(
        id = id,
        title = title,
        description = description,
        priority = priorityId
            .let(Priority::fromId)
    )

fun List<ToDoTaskEntity>.toDomain(): List<ToDoTask> =
    map(ToDoTaskEntity::toDomain)

fun ToDoTask.toEntity(): ToDoTaskEntity =
    ToDoTaskEntity(
        id = id,
        title = title,
        description = description,
        priorityId = priority.id,
    )
