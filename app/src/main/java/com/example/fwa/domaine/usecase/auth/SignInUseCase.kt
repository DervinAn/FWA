package com.example.fwa.domaine.usecase.auth

import com.example.fwa.domaine.repository.auth.AuthRepository


class SignInUseCase(private val repository: AuthRepository) {
        suspend fun execute(email : String,password : String): Result<Boolean> {
            return repository.signIn(email,password)
        }
}