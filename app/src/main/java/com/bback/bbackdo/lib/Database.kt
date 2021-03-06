package com.bback.bbackdo.lib

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Database {
    private val database = Firebase.database("https://android-bbackdo-default-rtdb.asia-southeast1.firebasedatabase.app/")

    fun getReference(path: String) = database.getReference(path)
    fun set(path: String, value: Any?) = getReference(path).setValue(value)
}