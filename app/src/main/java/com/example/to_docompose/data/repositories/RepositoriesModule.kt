package com.example.to_docompose.data.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun toDoTaskRepository(impl: ToDoTasksRepositoryImpl): ToDoTasksRepository
}
