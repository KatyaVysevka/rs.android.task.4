package com.example.workingwithstorage.fragments.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor(application: Application) : AndroidViewModel(application)