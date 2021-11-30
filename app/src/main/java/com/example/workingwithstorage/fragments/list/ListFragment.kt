package com.example.workingwithstorage.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.workingwithstorage.R
import com.example.workingwithstorage.common.logDebug
import com.example.workingwithstorage.databinding.FragmentListBinding
import com.example.workingwithstorage.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = requireNotNull(_binding)

    private val mFilmViewModel: FilmViewModel by viewModels()
    private val adapter = ListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //RecyclerView
        binding.recycler.adapter = adapter

        //FilmViewModel
        mFilmViewModel.allFilm.observe(viewLifecycleOwner) { film ->
            logDebug(film.toString())
            adapter.setData(film)
        }

        //add menu
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_filter -> {
                findNavController().navigate(R.id.action_listFragment_to_filterFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}