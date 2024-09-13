package com.example.to_docompose.di

import android.content.Context
import androidx.room.Room
import com.example.to_docompose.data.db.DbConstants
import com.example.to_docompose.data.db.ToDoTasksDao
import com.example.to_docompose.data.db.ToDoTasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ToDoTasksDatabase =
        Room
            .databaseBuilder(
                context,
                ToDoTasksDatabase::class.java,
                DbConstants.DB_NAME,
            )
            .build()

    @Singleton
    @Provides
    fun provideToDoTasksDao(
        database: ToDoTasksDatabase
    ): ToDoTasksDao =
        database.toDoTasksDao()
}
