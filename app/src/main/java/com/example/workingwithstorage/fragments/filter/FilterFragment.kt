package com.example.workingwithstorage.fragments.filter

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.workingwithstorage.R
import com.example.workingwithstorage.common.logDebug
import com.example.workingwithstorage.data.TYPE_DB_KEY
import com.example.workingwithstorage.data.TypeDB


class FilterFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val viewModel: FilterViewModel by viewModels()


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(TYPE_DB_KEY)) {
            val typeDB = sharedPreferences?.getString(key, "")
            logDebug("onSharedPreferenceChanged - typeDB: $typeDB")
            when (typeDB) {
                resources.getStringArray(R.array.db_values)[0] -> viewModel.onTypeDBSelected(TypeDB.ROOM)
                resources.getStringArray(R.array.db_values)[1] -> viewModel.onTypeDBSelected(TypeDB.SQL_LITE)
            }
        }
    }

}