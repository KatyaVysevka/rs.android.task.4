package com.example.workingwithstorage.data.sqlite

import android.content.ContentValues
import com.example.workingwithstorage.data.COLUMN_COUNTRY
import com.example.workingwithstorage.data.COLUMN_TITLE
import com.example.workingwithstorage.data.COLUMN_YEAR
import com.example.workingwithstorage.data.DatabaseStrategy
import com.example.workingwithstorage.data.TABLE_NAME
import com.example.workingwithstorage.model.Film
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.LazyThreadSafetyMode.NONE

/**
 * Потому что мы хотим инициальизировать базу только в одном месте -
 * [com.example.workingwithstorage.data.room.FilmDatabase] - мы делает [sqlLite]
 * переменную Lazy. Это значит, что она пбудет проиничена только во время обращения к ней.
 */
@Singleton
class SQLiteDao @Inject constructor(
    private val sqlLite: Lazy<FilmSQLiteHelper>,
) : DatabaseStrategy {

    private val triggerRefreshFlow = MutableSharedFlow<Unit>()
    private val writableDb by lazy(NONE) {
        sqlLite.get().writableDatabase
    }

    override suspend fun addFilm(film: Film) {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, film.title)
            put(COLUMN_COUNTRY, film.country)
            put(COLUMN_YEAR, film.year)
        }
        writableDb.insert(TABLE_NAME, null, values)
        triggerRefreshFlow.emit(Unit)
    }

    override suspend fun updateFilm(film: Film) {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, film.title)
            put(COLUMN_COUNTRY, film.country)
            put(COLUMN_YEAR, film.year)
        }
        writableDb.update(TABLE_NAME, values, "id = ?", arrayOf(film.id.toString()))
        triggerRefreshFlow.emit(Unit)
    }

    override suspend fun deleteFilm(film: Film) {
        writableDb.delete(TABLE_NAME, "id = ?", arrayOf(film.id.toString()))
        triggerRefreshFlow.emit(Unit)
    }

    override fun readAllData(): Flow<List<Film>> {
        return triggerRefreshFlow
            .onStart { emit(Unit) }
            .map { queryAllFilms() }
    }

    override fun sortedByTitle(): Flow<List<Film>> {
        return triggerRefreshFlow
            .onStart { emit(Unit) }
            .map { queryAllFilms(orderBy = "$COLUMN_TITLE ASC") }
    }

    override fun sortedByCountry(): Flow<List<Film>> {
        return triggerRefreshFlow
            .onStart { emit(Unit) }
            .map { queryAllFilms(orderBy = "$COLUMN_COUNTRY ASC") }
    }

    override fun sortedByYear(): Flow<List<Film>> {
        return triggerRefreshFlow
            .onStart { emit(Unit) }
            .map { queryAllFilms(orderBy = "$COLUMN_YEAR ASC") }
    }

    private fun queryAllFilms(orderBy: String? = null): List<Film> {
        val listOfTopics = mutableListOf<Film>()
        val cursor = writableDb.query(
            TABLE_NAME, null,
            null, null, null, null,
            orderBy
        )

        cursor.use {
            it.apply {
                while (moveToNext()) {
                    listOfTopics.add(
                        Film(
                            getInt(0),
                            getString(1),
                            getString(2),
                            getInt(3)
                        )
                    )
                }
            }
        }

        return listOfTopics
    }
}