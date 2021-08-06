package com.example.bbackdo.lib

import com.example.bbackdo.dto.User
import com.google.firebase.auth.FirebaseAuth

object Authentication {

    val uid
        get() = FirebaseAuth.getInstance().currentUser?.uid
    var user: User? = null
    fun isLoggedIn() = when (FirebaseAuth.getInstance().currentUser) {
        null -> false
        else -> true
    }
}