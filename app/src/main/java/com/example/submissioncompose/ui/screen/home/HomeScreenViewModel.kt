package com.example.submissioncompose.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissioncompose.data.GameRepository
import com.example.submissioncompose.model.GameItem
import com.example.submissioncompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: GameRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Map<Char, List<GameItem>>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Map<Char, List<GameItem>>>> get() = _uiState

    private val _searchResult = MutableStateFlow<List<GameItem>>(emptyList())
    val searchResult: StateFlow<List<GameItem>> get() = _searchResult

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun getAllGames() {
        viewModelScope.launch {
            repository.getSortedAndGroupedGame()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { groupedGameItems ->
                    _uiState.value = UiState.Success(groupedGameItems)
                }
        }
    }

    fun searchGames() {
        val currentQuery = _query.value
        viewModelScope.launch {
            repository.searchGames(currentQuery)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { searchResult ->
                    _searchResult.value = searchResult
                }
        }
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}

