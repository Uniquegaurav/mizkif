package com.example.mymiso.presentation.profile_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentProfileScreenBinding
import com.example.mymiso.presentation.profile_screen.ui.fragments.ShowUserFragment

class FragmentProfileScreen : Fragment(R.layout.fragment_profile_screen) {

    private lateinit var _binding: FragmentProfileScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction()
            .replace(R.id.flFragmentProfileScreen, ShowUserFragment())
            .commit()
    }
}