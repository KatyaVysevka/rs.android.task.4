package com.example.workingwithstorage.data.repository

import com.example.workingwithstorage.ApplicationScope
import com.example.workingwithstorage.IoDispatcher
import com.example.workingwithstorage.common.logDebug
import com.example.workingwithstorage.data.DatabaseStrategy
import com.example.workingwithstorage.data.PreferenceManager
import com.example.workingwithstorage.data.TypeDB
import com.example.workingwithstorage.data.room.FilmDao
import com.example.workingwithstorage.data.sqlite.SQLiteDao
import com.example.workingwithstorage.model.Film
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class FilmRepository @Inject constructor(
    private val filmSQLite: SQLiteDao,
    private val filmDao: FilmDao,
    private val preferencesManager: PreferenceManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApplicationScope private val appScope: CoroutineScope
) {

    private val filmBDFlow = MutableSharedFlow<DatabaseStrategy>(replay = 1)

    init {
        appScope.launch {
            preferencesManager.typeDbFlow
                .collect {
                    when (it) {
                        TypeDB.ROOM -> filmBDFlow.emit(filmDao)
                        TypeDB.SQL_LITE -> filmBDFlow.emit(filmSQLite)
                    }
                }
        }
    }

    fun getAll(): Flow<List<Film>> {
        return filmBDFlow
            .flatMapLatest {
                it.readAllData()
            }
            .flowOn(ioDispatcher)
    }

    fun sortedByTitle(): Flow<List<Film>> {
        return filmBDFlow
            .flatMapLatest { it.sortedByTitle() }
            .flowOn(ioDispatcher)
    }

    fun sortedByCountry(): Flow<List<Film>> {
        return filmBDFlow
            .flatMapLatest { it.sortedByCountry() }
            .flowOn(ioDispatcher)
    }

    fun sortedByYear(): Flow<List<Film>> {
        return filmBDFlow
            .flatMapLatest { it.sortedByYear() }
            .flowOn(ioDispatcher)
    }

    suspend fun addFilm(film: Film) = withContext(ioDispatcher) {
       filmBDFlow.first().addFilm(film)
    }

    suspend fun updateFilm(film: Film) = withContext(ioDispatcher) {
        filmBDFlow.first().updateFilm(film)
    }

    suspend fun deleteFilm(film: Film) = withContext(ioDispatcher) {
        filmBDFlow.first().deleteFilm(film)
    }
}