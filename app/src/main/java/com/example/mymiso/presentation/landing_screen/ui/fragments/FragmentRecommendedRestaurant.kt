package com.example.mymiso.presentation.landing_screen.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentRecommendedRestaurantGridBinding
import com.example.mymiso.domain.use_cases.GetRecommendedRestaurant
import com.example.mymiso.presentation.landing_screen.adapter.RecommendedRestaurantRecyclerViewAdapter
import com.example.mymiso.presentation.landing_screen.viewmodel.LandingScreenViewModel

class FragmentRecommendedRestaurant : Fragment(R.layout.fragment_recommended_restaurant_grid) {
    private lateinit var recommendedRestaurantAdapter: RecommendedRestaurantRecyclerViewAdapter
    private var _binding: FragmentRecommendedRestaurantGridBinding? = null
    private lateinit var viewModel: LandingScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendedRestaurantGridBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
        recommendedRestaurantAdapter.differ.submitList(GetRecommendedRestaurant().invoke())

//        recommendedRestaurantAdapter.setOnItemClickListener { restaurant ->
//            viewModel.setSelectedRestaurant(restaurant)
//        }


    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(requireActivity())[LandingScreenViewModel::class.java]
    }

    private fun setUpRecyclerView() {
        recommendedRestaurantAdapter = RecommendedRestaurantRecyclerViewAdapter()
        _binding?.recommendedRestaurantRecyclerView?.apply {
            adapter = recommendedRestaurantAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        }
    }
}