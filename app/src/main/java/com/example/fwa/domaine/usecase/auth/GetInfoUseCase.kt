package com.example.fwa.domaine.usecase.auth

import com.example.fwa.domaine.repository.auth.AuthRepository
import com.example.fwp.User

class GetInfoUseCase(private val repository: AuthRepository) {
    suspend fun execute(uid: String): Result<User> {
        return repository.getUserInformation(uid)
    }
}