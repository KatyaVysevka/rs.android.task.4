package com.example.workingwithstorage.data

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Вижу есть сетап под Dagger/Dagger Hilt, но он не используется :(
@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.createDataStore(FILM_PREFERENCES)

    val typeDB: Flow<String> = dataStore.data.map {
        it[PreferencesKeys.TYPE_DB] ?: TypeDB.ROOM.name
    }


    suspend fun updateTypeDB(typeDB: TypeDB) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TYPE_DB] = typeDB.name
        }
    }


    suspend fun updateTrigger(str: String) {
        dataStore.edit { preferences ->
            if (preferences[PreferencesKeys.UPDATE_DB] == str) {
                preferences[PreferencesKeys.UPDATE_DB] = "trigger"
            } else {
                preferences[PreferencesKeys.UPDATE_DB] = str
            }
        }
    }

    val trigger: Flow<String> = dataStore.data.map {
        it[PreferencesKeys.UPDATE_DB] ?: ""
    }
}

object PreferencesKeys {

    val TYPE_DB = preferencesKey<String>(TYPE_DB_KEY)
    val UPDATE_DB = preferencesKey<String>(TRIGGER_KEY)
}

const val FILM_PREFERENCES = "film_preferences"
const val TYPE_DB_KEY = "db_type"

const val TRIGGER_KEY = "trigger"
