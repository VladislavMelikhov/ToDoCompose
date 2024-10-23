package com.example.to_docompose.di

import com.example.to_docompose.utils.coroutines.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApplicationScope(): ApplicationScope =
        ApplicationScope(SupervisorJob() + Dispatchers.IO)
}
