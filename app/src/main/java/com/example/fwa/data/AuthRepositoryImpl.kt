package com.example.fwa.data

import com.example.fwa.domaine.repository.auth.AuthRepository
import com.example.fwp.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override suspend  fun signUp(
        email: String,
        password: String,
        username: String
    ): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: return Result.failure(Exception("UID is null"))
            val photoUrl = authResult.user?.photoUrl?.toString() ?: ""

            val user = User(
                name = username,
                email = email,
                photoUrl = photoUrl,
                location = ""
            )

            db.collection("users").document(uid).set(user).await()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserInformation(uid: String?): Result<User> {
        return try {
            if (uid == null) return Result.failure(Exception("UID is null"))

            val doc = db.collection("users").document(uid).get().await()
            val user = doc.toObject(User::class.java) ?: return Result.failure(Exception("User not found"))
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
