package com.example.workingwithstorage.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.workingwithstorage.databinding.FragmentAddBinding
import com.example.workingwithstorage.model.Film
import com.example.workingwithstorage.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding get() = requireNotNull(_binding)

    private val filmViewModel by activityViewModels<FilmViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

    }

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
            filmViewModel.addFilm(film)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_LONG).show()

            //navigate back
            findNavController().navigateUp()
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