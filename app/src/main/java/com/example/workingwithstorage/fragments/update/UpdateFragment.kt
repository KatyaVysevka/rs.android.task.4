package com.example.workingwithstorage.fragments.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workingwithstorage.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding: FragmentUpdateBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}