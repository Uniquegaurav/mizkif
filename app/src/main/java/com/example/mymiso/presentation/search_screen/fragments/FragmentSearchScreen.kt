package com.example.mymiso.presentation.search_screen.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentSearchScreenBinding
import com.example.mymiso.presentation.search_screen.SearchResultAdapter
import com.example.mymiso.presentation.search_screen.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentSearchScreen : Fragment(R.layout.fragment_search_screen) {
    private var _binding: FragmentSearchScreenBinding? = null
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: SearchResultAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        _binding?.searchScreenEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
//
                searchViewModel.updateQuery(s.toString())
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.searchResults.collectLatest { state ->
                    Log.d("FragmentSearchScreen", "State: $state")
                    searchAdapter.differ.submitList(state)
                }
            }
        }


    }

    private fun init() {
        setupViewModel()
        setUpAdapter()
    }

    private fun setupViewModel() {
        searchViewModel = ViewModelProvider(this)[(SearchViewModel::class.java)]
    }

    private fun setUpAdapter() {
        searchAdapter = SearchResultAdapter()
        _binding?.searchResultRecyclerView?.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}