package com.example.mymiso.presentation.home_screen.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mymiso.R
import com.example.mymiso.presentation.home_screen.viewmodel.HomeScreenViewModel

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