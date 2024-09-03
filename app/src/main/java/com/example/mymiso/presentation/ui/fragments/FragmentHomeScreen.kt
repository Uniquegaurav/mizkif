package com.example.mymiso.presentation.ui.fragments

import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mymiso.R
import com.example.mymiso.presentation.adapter.AllRestaurantRecyclerViewAdapter
import com.example.mymiso.presentation.adapter.RecommendedRestaurantRecyclerViewAdapter
import com.example.mymiso.presentation.ui.viewmodels.HomeScreenViewModel

class FragmentHomeScreen : Fragment(R.layout.fragment_home_screen) {
    private lateinit var vieModel: HomeScreenViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .add(R.id.search_bar_container, FragmentSearchBar())
            .add(R.id.recommended_restaurants_container, FragmentRecommendedRestaurant())
            .add(R.id.all_restaurants_container, FragmentAllRestaurant())
            .commit()
    }
}