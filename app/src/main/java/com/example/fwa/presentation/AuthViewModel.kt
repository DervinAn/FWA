package com.example.fwa.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fwa.domaine.usecase.auth.GetInfoUseCase
import com.example.fwa.domaine.usecase.auth.SignInUseCase
import com.example.fwa.domaine.usecase.auth.SignUpUseCase
import com.example.fwp.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authSi: SignInUseCase,
    private val authSp: SignUpUseCase,
    private val authGetInfo: GetInfoUseCase,
) : ViewModel() {

    private val _authState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val authState: StateFlow<UiState<Boolean>> get() = _authState

    private val _userInfoState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val userInfoState: StateFlow<UiState<User>> get() = _userInfoState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading

            val result = authSi.execute(email, password)
            _authState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading

            val result = authSp.execute(email, password, username)
            _authState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun getUserInfo(uid: String?) {
        viewModelScope.launch {
            _userInfoState.value = UiState.Loading

            val result = authGetInfo.execute(uid.toString())
            _userInfoState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun clearAuthState() {
        _authState.value = UiState.Idle
    }

    fun clearUserInfoState() {
        _userInfoState.value = UiState.Idle
    }
}
