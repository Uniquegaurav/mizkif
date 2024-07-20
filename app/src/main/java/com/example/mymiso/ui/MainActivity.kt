package com.example.mymiso.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mymiso.R
import com.example.mymiso.db.UserDatabase
import com.example.mymiso.repository.NewRepository
import com.example.mymiso.ui.viewmodels.MainViewModel
import com.example.mymiso.ui.viewmodels.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun createViewModel() {
        val newRepository = NewRepository(UserDatabase.invoke(this))
        viewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(newRepository)
        )[MainViewModel::class.java]
    }
}