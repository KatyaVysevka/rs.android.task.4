package com.example.workingwithstorage.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefsManager = PreferenceManager.getDefaultSharedPreferences(context)
    private val flowPreferences = FlowSharedPreferences(prefsManager)

    private val sortModePref = flowPreferences.getString(KEY_SORT_TYPE, "None")
    private val typeDBPref = flowPreferences.getString(KEY_TYPE_DB, "Room")

    val sortModeFlow: Flow<SortMode>
        get() = sortModePref.asFlow().distinctUntilChanged()
            .map { modeString ->
                when (modeString) {
                    "Title" -> SortMode.BY_TITLE
                    "Country" -> SortMode.BY_COUNTRY
                    "Year" -> SortMode.BY_YEAR
                    else -> SortMode.NONE
                }
            }

    val typeDbFlow: Flow<TypeDB>
        get() = typeDBPref.asFlow().distinctUntilChanged()
            .map { typeString ->
                when (typeString) {
                    "Room" -> TypeDB.ROOM
                    "SQLite" -> TypeDB.SQL_LITE
                    else -> error("Invalid `typeDB` value: $typeString")
                }
            }

    suspend fun updateTypeDB(typeDB: TypeDB) {
        typeDBPref.setAndCommit(typeDB.name)
    }

    companion object {
        const val KEY_SORT_TYPE = "sort_type"
        const val KEY_TYPE_DB = "db_type"
    }
}