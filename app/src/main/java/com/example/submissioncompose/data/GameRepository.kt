package com.example.submissioncompose.data

import com.example.submissioncompose.model.GameItem
import com.example.submissioncompose.model.GamesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameRepository {
    private val gameItem = mutableListOf<GameItem>()

    init {
        if (gameItem.isEmpty()) {
            GamesData.game.forEach {
                gameItem.add(GameItem(it, 0))
            }
        }
    }

    fun getSortedAndGroupedGame(): Flow<Map<Char, List<GameItem>>> {
        return flow {
            val sortedGames = gameItem.sortedBy { it.item.gameName }
            val groupedGames = sortedGames.groupBy { it.item.gameName[0] }
            emit(groupedGames)
        }
    }

    fun searchGames(query: String): Flow<List<GameItem>> {
        return flow {
            val filteredGames = gameItem.filter {
                it.item.gameName.contains(query, ignoreCase = true)
            }
            emit(filteredGames)
        }
    }

    companion object {
        @Volatile
        private var instance: GameRepository? = null

        fun getInstance(): GameRepository = instance ?: synchronized(this) {
            GameRepository().apply {
                instance = this
            }
        }
    }
}