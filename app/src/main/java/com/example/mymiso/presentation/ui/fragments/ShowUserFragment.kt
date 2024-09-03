package com.example.mymiso.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymiso.R
import com.example.mymiso.presentation.adapter.UserRecyclerViewAdapter
import com.example.mymiso.databinding.FragmentShowUserBinding
import com.example.mymiso.data.db.UserDatabase
import com.example.mymiso.domain.repository.NewRepository
import com.example.mymiso.presentation.ui.viewmodels.MainViewModel
import com.example.mymiso.presentation.ui.viewmodels.ViewModelProviderFactory
import com.example.mymiso.util.Resource

class ShowUserFragment : Fragment(R.layout.fragment_show_user) {
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserRecyclerViewAdapter
    private lateinit var binding: FragmentShowUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        userAdapter.setOnItemClickListener {

        }

        val newRepository = NewRepository(UserDatabase(requireContext()))
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProviderFactory(newRepository)
        )[MainViewModel::class.java]

        viewModel.users.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Error -> {
                    response.errorMessage?.let {
                        Log.e("tag", it)
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    response.data?.let {
                        userAdapter.differ.submitList(it.users)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        userAdapter = UserRecyclerViewAdapter()
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}