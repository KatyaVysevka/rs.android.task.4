package com.example.workingwithstorage.fragments.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workingwithstorage.data.PreferenceManager
import com.example.workingwithstorage.data.TypeDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val preferencesManager: PreferenceManager) :
    ViewModel() {

    fun onTypeDBSelected(typeDB: TypeDB) = viewModelScope.launch {
        preferencesManager.updateTypeDB(typeDB)
    }
}