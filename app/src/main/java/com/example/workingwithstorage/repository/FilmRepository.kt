package com.example.workingwithstorage.repository

import com.example.workingwithstorage.common.logDebug
import com.example.workingwithstorage.data.DatabaseStrategy
import com.example.workingwithstorage.data.PreferenceManager
import com.example.workingwithstorage.data.SQLite.SQLiteDao
import com.example.workingwithstorage.data.TypeDB
import com.example.workingwithstorage.data.room.FilmDao
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmRepository @Inject constructor(
    private val filmSQLite: SQLiteDao,
    private val filmDao: FilmDao,
    private val preferencesManager: PreferenceManager
) {

    private var filmBD: DatabaseStrategy = filmDao

    val job = CoroutineScope(Dispatchers.IO).launch {
        preferencesManager.typeDB.collect {
            when (it) {
                TypeDB.ROOM.name -> filmBD = filmDao
                TypeDB.SQL_LITE.name -> filmBD = filmSQLite
            }
        }
    }


    fun getAll(): Flow<List<Film>> {
        return filmBD.readAllData()
    }

    fun sortedByTitle(): Flow<List<Film>> {
        return filmBD.sortedByTitle()
    }

    fun sortedByCountry(): Flow<List<Film>> {
        return filmBD.sortedByCountry()
    }

    fun sortedByYear(): Flow<List<Film>> {
        return filmBD.sortedByYear()
    }

    suspend fun addFilm(film: Film) {
        filmBD.addFilm(film)
    }

    suspend fun updateFilm(film: Film) {
        filmBD.updateFilm(film)
    }

    suspend fun deleteFilm(film: Film) {
        filmBD.deleteFilm(film)
    }


}