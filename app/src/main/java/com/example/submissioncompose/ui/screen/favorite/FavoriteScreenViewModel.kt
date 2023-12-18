package com.example.submissioncompose.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.GameRepository
import com.example.submissioncompose.model.GameItem
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(private val repository: GameRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<GameItem>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<GameItem>>> get() = _uiState

    val favoriteGames: Flow<List<GameItem>> = repository.getGamesFavorite()

    fun getAllFavoriteGames() {
        viewModelScope.launch {
                try {
                    _uiState.value = UiState.Loading

                    val favoriteGameItems = repository.getGamesFavorite().first()
                    _uiState.value = UiState.Success(favoriteGameItems)
                } catch (e: Exception) {
                    _uiState.value = UiState.Error("Failed to fetch favorite games: ${e.message}")
                }
        }
    }
}