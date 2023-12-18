package com.example.submissioncompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissioncompose.data.GameRepository
import com.example.submissioncompose.ui.screen.home.HomeScreenViewModel

class ViewModelFactory(private val repository: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java))
            return HomeScreenViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}