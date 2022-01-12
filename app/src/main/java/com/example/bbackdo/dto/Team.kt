package com.example.bbackdo.dto

data class Team(var tid: String? = "",
                var result: Boolean? = false,
                var members: Map<String, Any>? = null
                )