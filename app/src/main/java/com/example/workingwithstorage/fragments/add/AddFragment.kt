package com.example.workingwithstorage.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.workingwithstorage.R
import com.example.workingwithstorage.databinding.FragmentAddBinding
import com.example.workingwithstorage.model.Film
import com.example.workingwithstorage.viewmodel.FilmViewModel
import kotlinx.coroutines.InternalCoroutinesApi


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding get() = requireNotNull(_binding)

    @InternalCoroutinesApi
    private lateinit var mFilmViewModel: FilmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFilmViewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

    }

    @InternalCoroutinesApi
    private fun insertDataToDatabase() {
        if (inputCheck(
                binding.editTitle.text.toString(),
                binding.editCountry.text.toString(),
                binding.editYear.text.toString()
            )
        ) {
            //create film object
            val title = binding.editTitle.text.toString()
            val country = binding.editCountry.text.toString()
            val year = binding.editYear.text
            val film = Film(0, title, country, Integer.parseInt(year.toString()))

            //Add data to database
            mFilmViewModel.addFilm(film)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()

            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, country: String, year: String): Boolean {
        return !(title.isBlank() || country.isBlank() || year.isBlank())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}