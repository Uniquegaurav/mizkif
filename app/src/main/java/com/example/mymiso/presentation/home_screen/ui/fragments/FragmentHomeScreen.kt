package com.example.mymiso.presentation.home_screen.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentHomeScreenBinding
import com.example.mymiso.presentation.home_screen.viewmodel.HomeScreenViewModel

class FragmentHomeScreen : Fragment(R.layout.fragment_home_screen) {
    private lateinit var vieModel: HomeScreenViewModel

    private var _binding: FragmentHomeScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction()
            .add(R.id.search_bar_container, FragmentSearchBar())
            .add(R.id.recommended_restaurants_container, FragmentRecommendedRestaurant())
            .add(R.id.all_restaurants_container, FragmentAllRestaurant())
            .commit()

    }
}