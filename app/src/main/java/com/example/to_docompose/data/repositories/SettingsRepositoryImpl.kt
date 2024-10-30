package com.example.to_docompose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.to_docompose.data.models.TasksSortPolicy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SettingsRepository {

    companion object {
        private const val DATA_STORE_NAME = "settingsDataStore"
        private val TASKS_SORT_POLICY_KEY = intPreferencesKey("tasksSortPolicy")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

    override val sortPolicy: Flow<TasksSortPolicy> =
        context.dataStore.data.map { preferences ->
            preferences[TASKS_SORT_POLICY_KEY]
                ?.let(TasksSortPolicy.Companion::fromId)
                ?: TasksSortPolicy.DEFAULT
        }

    override suspend fun saveSortPolicy(sortPolicy: TasksSortPolicy) {
        context.dataStore.edit { preferences ->
            preferences[TASKS_SORT_POLICY_KEY] = sortPolicy.id
        }
    }
}
