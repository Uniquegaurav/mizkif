package com.example.mymiso.presentation.landing_screen.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentSearchBarBinding


class FragmentSearchBar : Fragment(R.layout.fragment_search_bar) {

    private var _binding: FragmentSearchBarBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBarBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.searchBarEditText?.setOnClickListener {
            Log.d("FragmentSearchBar", "searchBarEditText clicked")
        }
    }
}