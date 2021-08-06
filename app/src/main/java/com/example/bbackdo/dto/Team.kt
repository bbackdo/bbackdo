package com.example.bbackdo.dto

data class Team(var Room: String = "",
                var result: Boolean = false,
                var members: Map<String, Any>? = null
                )