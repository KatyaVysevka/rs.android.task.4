package com.example.workingwithstorage.fragments.list

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.workingwithstorage.R
import com.example.workingwithstorage.common.logDebug
import com.example.workingwithstorage.databinding.FragmentListBinding
import com.example.workingwithstorage.viewmodel.FilmViewModel
import kotlinx.coroutines.InternalCoroutinesApi


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = requireNotNull(_binding)

    @InternalCoroutinesApi
    private lateinit var mFilmViewModel: FilmViewModel
    private val adapter = ListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //RecyclerView
        binding.recycler.adapter = adapter

        //FilmViewModel
        mFilmViewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        mFilmViewModel.allFilm.observe(viewLifecycleOwner, Observer { film ->
            adapter.setData(film)
        })

        //add menu
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter ->
                findNavController().navigate(R.id.action_listFragment_to_filterFragment)
            //       R.id.menu_sql -> mFilmViewModel
        }

        return super.onOptionsItemSelected(item)
    }

    @InternalCoroutinesApi
    var listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { preference, key ->
            val value = preference.getString(key, "Title")
            when (value) {
                "Title" -> mFilmViewModel.sortedByTitle()
                "Country" -> mFilmViewModel.sortedByCountry()
                "Year" -> mFilmViewModel.sortedByYear()
            }
            logDebug(" ключ $value")
        }

    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.registerOnSharedPreferenceChangeListener(listener)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}