package com.example.workingwithstorage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.workingwithstorage.data.PreferenceManager
import com.example.workingwithstorage.data.SortMode
import com.example.workingwithstorage.data.repository.FilmRepository
import com.example.workingwithstorage.model.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

// Довольно странно, что эта ViewModel лежит отдельно от других (другие лежат рядом с фрагментами).
// Я бы переместил эту ViewModel поближе к `fragment.*` пакету.
@HiltViewModel
class FilmViewModel @Inject constructor(
    application: Application,
    private val repository: FilmRepository,
    private val preferenceManager: PreferenceManager,
) : AndroidViewModel(application) {

    val allFilm: LiveData<List<Film>>

    init {
        allFilm = preferenceManager.sortModeFlow
            .flatMapLatest { mode ->
                when (mode) {
                    SortMode.NONE -> repository.getAll()
                    SortMode.BY_TITLE -> repository.sortedByTitle()
                    SortMode.BY_COUNTRY -> repository.sortedByCountry()
                    SortMode.BY_YEAR -> repository.sortedByYear()
                }
            }
            .asLiveData()
    }

    fun addFilm(film: Film) {
        viewModelScope.launch {
            repository.addFilm(film)
        }
    }

    fun updateFilm(film: Film) {
        viewModelScope.launch {
            repository.updateFilm(film)
        }
    }

    fun deleteFilm(film: Film) {
        viewModelScope.launch {
            repository.deleteFilm(film)
        }
    }
}