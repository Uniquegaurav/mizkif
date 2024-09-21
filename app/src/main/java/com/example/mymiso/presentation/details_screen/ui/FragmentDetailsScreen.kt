package com.example.mymiso.presentation.details_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentDetailsScreenBinding


class FragmentDetailsScreen : Fragment(R.layout.fragment_details_screen) {

    private lateinit var fragmentDetailsScreenBinding: FragmentDetailsScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDetailsScreenBinding =
            FragmentDetailsScreenBinding.inflate(inflater, container, false)
        return fragmentDetailsScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentDetailsScreenBinding.placeOrderButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentDetailsScreen_to_orderTrackingFragment)
        }
    }

}