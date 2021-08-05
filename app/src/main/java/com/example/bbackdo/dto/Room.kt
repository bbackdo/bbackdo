package com.example.bbackdo.dto

data class Room (var pw: String = "",
                 var numOfTeams: Int = 0,
                 var teams: Map<String, Any>? = null
)