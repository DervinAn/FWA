package com.example.fwa.domaine

sealed class Result1<out T> {
    data class Success<T>(val data: T) : Result1<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : Result1<Nothing>()
    object Loading : Result1<Nothing>()
}
