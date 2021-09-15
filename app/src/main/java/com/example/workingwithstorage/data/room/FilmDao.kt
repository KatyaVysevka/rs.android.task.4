package com.example.workingwithstorage.data.room

import androidx.room.*
import com.example.workingwithstorage.data.DatabaseStrategy
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmDao: DatabaseStrategy {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override suspend fun addFilm(film: Film)

    @Update
    override suspend fun updateFilm (film: Film)

    @Delete
    override suspend fun deleteFilm (film: Film)

    @Query("SELECT * FROM film_table ORDER BY id ASC")
    override fun readAllData(): Flow<List<Film>>

    @Query("SELECT * FROM film_table ORDER BY title ASC")
    override fun sortedByTitle(): Flow<List<Film>>

    @Query("SELECT * FROM film_table ORDER BY country ASC")
    override fun sortedByCountry(): Flow<List<Film>>

    @Query("SELECT * FROM film_table ORDER BY year ASC")
    override fun sortedByYear(): Flow<List<Film>>

}