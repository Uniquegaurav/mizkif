package com.example.mymiso.presentation.landing_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentLandingScreenBinding
import com.example.mymiso.presentation.landing_screen.ui.fragments.FragmentAllRestaurant
import com.example.mymiso.presentation.landing_screen.ui.fragments.FragmentRecommendedRestaurant
import com.example.mymiso.presentation.landing_screen.ui.fragments.FragmentSearchBar
import com.example.mymiso.presentation.landing_screen.viewmodel.LandingScreenViewModel
class FragmentLandingScreen : Fragment(R.layout.fragment_landing_screen) {

    private lateinit var viewModel: LandingScreenViewModel
    private var _binding: FragmentLandingScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .add(R.id.search_bar_container, FragmentSearchBar())
            .add(R.id.recommended_restaurants_container, FragmentRecommendedRestaurant())
            .add(R.id.all_restaurants_container, FragmentAllRestaurant())
            .commit()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
