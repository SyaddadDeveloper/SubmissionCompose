package com.example.submissioncompose.di

import com.example.submissioncompose.data.GameRepository

object Injection {
    fun provideRepository(): GameRepository {
        return GameRepository.getInstance()
    }
}