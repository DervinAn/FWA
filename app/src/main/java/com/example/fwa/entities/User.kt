package com.example.fwp

import com.google.firebase.firestore.Exclude

data class User(
    @get:Exclude var id: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val location: String = ""
)