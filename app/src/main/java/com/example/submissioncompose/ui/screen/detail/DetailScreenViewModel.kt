package com.example.submissioncompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.GameRepository
import com.example.submissioncompose.model.GameItem
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repository: GameRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<GameItem>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<GameItem>> get() = _uiState

    fun getGameById(gameId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getGameItemById(gameId))
        }
    }

    fun addToFavorites(gameId: String) {
        viewModelScope.launch {
            repository.addToFavorites(gameId)
        }
    }

    fun removeFromFavorite(gameId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(gameId)
        }
    }

    fun checkFavorite(gameId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(gameId)
            onResult(isFavorite)
        }
    }
}