package com.example.fwa.domaine.usecase.auth

import com.example.fwa.domaine.repository.auth.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
        suspend fun execute(email : String,password : String,username: String): Result<Boolean> {
            return repository.signUp(email,password, username)
        }
}