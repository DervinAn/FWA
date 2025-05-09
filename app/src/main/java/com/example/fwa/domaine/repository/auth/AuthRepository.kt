    package com.example.fwa.domaine.repository.auth

    import com.example.fwp.User

    interface AuthRepository {
        suspend fun signIn(email : String, password: String): Result<Boolean>

        suspend fun signUp(email : String, password: String,username : String): Result<Boolean>

        suspend fun getUserInformation(uid: String?): Result<User>

    }