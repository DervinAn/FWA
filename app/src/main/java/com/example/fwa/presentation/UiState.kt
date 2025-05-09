package com.example.fwa.presentation

sealed class UiState<out T> {
    object Idle : UiState<Nothing>() // Optional: default state
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error(val message: String): UiState<Nothing>()
}
