package com.example.mymiso.presentation.home_screen.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentAllRestaurantBinding
import com.example.mymiso.domain.use_cases.GetAllRestaurant
import com.example.mymiso.presentation.home_screen.adapter.AllRestaurantRecyclerViewAdapter
import com.example.mymiso.presentation.home_screen.viewmodel.HomeScreenViewModel

class FragmentAllRestaurant : Fragment(R.layout.fragment_all_restaurant) {

    private lateinit var allRestaurantRecyclerViewAdapter: AllRestaurantRecyclerViewAdapter
    private lateinit var viewModel: HomeScreenViewModel
    private var _binding: FragmentAllRestaurantBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllRestaurantBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
        allRestaurantRecyclerViewAdapter.differ.submitList(GetAllRestaurant().invoke())
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(requireActivity())[HomeScreenViewModel::class.java]
    }

    private fun setUpRecyclerView() {
        allRestaurantRecyclerViewAdapter = AllRestaurantRecyclerViewAdapter()
        _binding?.allRestaurantRecyclerView?.apply {
            adapter = allRestaurantRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}