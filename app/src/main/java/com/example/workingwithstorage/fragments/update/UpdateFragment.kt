package com.example.workingwithstorage.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.workingwithstorage.R
import com.example.workingwithstorage.databinding.FragmentUpdateBinding
import com.example.workingwithstorage.model.Film
import com.example.workingwithstorage.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding: FragmentUpdateBinding
        get() = requireNotNull(_binding)

    private val args by navArgs<UpdateFragmentArgs>()
    private val filmViewModel by activityViewModels<FilmViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            updateTitle.setText(args.currentFilm.title)
            updateCountry.setText(args.currentFilm.country)
            updateYear.setText(args.currentFilm.year.toString())

            updateBtn.setOnClickListener {
                updateItem()
            }
        }
        //add menu
        setHasOptionsMenu(true)
    }

    private fun updateItem() {
        if (inputCheck(binding.updateTitle.text.toString(),
                binding.updateCountry.text.toString(),
                binding.updateYear.text.toString() )){
            val title = binding.updateTitle.text.toString()
            val country = binding.updateCountry.text.toString()
            val year = Integer.parseInt(binding.updateYear.text.toString())
            //create user Object
            val updatedFilm = Film(args.currentFilm.id, title, country, year)
            //update currentUser
            filmViewModel.updateFilm(updatedFilm)
            Toast.makeText(requireContext(), "Update successfully", Toast.LENGTH_SHORT).show()
            //navigated back
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck (title: String, country:String, year: String): Boolean{
        return !(title.isBlank() || country.isBlank() || year.isBlank())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteFilm()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteFilm() {
        AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                filmViewModel.deleteFilm(args.currentFilm)
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${args.currentFilm.title}",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
            }
            .setNegativeButton ("No") { _, _ -> }
            .setTitle("Delete ${args.currentFilm.title}?")
            .setMessage("Are you sure you want to delete ${args.currentFilm.title}?")
            .create()
            .show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}