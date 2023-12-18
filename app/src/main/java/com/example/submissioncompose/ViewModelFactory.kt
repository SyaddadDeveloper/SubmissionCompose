package com.example.submissioncompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissioncompose.data.GameRepository
import com.example.submissioncompose.ui.screen.detail.DetailScreenViewModel
import com.example.submissioncompose.ui.screen.favorite.FavoriteScreenViewModel
import com.example.submissioncompose.ui.screen.home.HomeScreenViewModel

class ViewModelFactory(private val repository: GameRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeScreenViewModel::class.java) -> HomeScreenViewModel(
                repository
            ) as T

            modelClass.isAssignableFrom(DetailScreenViewModel::class.java) -> DetailScreenViewModel(
                repository
            ) as T

            modelClass.isAssignableFrom(FavoriteScreenViewModel::class.java) -> FavoriteScreenViewModel(
                repository
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}