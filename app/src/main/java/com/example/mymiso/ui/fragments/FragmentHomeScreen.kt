package com.example.mymiso.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mymiso.R

class FragmentHomeScreen : Fragment(R.layout.fragment_home_screen) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.search_bar_container, FragmentSearchBar())
            .replace(R.id.recommended_restaurants_container, FragmentRecommendedRestaurant())
            .replace(R.id.all_restaurants_container, FragmentAllRestaurant())
            .commit()
    }

}