package com.example.workingwithstorage.fragments.filter

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.workingwithstorage.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : PreferenceFragmentCompat() {

    private val viewModel: FilterViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}