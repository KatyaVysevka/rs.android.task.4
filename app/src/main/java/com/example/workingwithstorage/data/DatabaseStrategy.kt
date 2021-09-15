package com.example.workingwithstorage.data

import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.flow.Flow

interface DatabaseStrategy {

    suspend fun addFilm(film: Film)
    suspend fun updateFilm(film: Film)
    suspend fun deleteFilm(film: Film)

    fun readAllData(): Flow<List<Film>>
    fun sortedByTitle(): Flow<List<Film>>
    fun sortedByCountry(): Flow<List<Film>>
    fun sortedByYear(): Flow<List<Film>>
}