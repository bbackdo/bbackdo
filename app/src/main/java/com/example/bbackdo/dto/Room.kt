package com.example.bbackdo.dto


data class Room (var manager: String? = "",
                 var memberNum: Int? = 0,
                 var password: String? = "",
                 var penalty: List<Int>? = listOf(0, 0, 0, 0, 0),
                 var rid: String? = "",
                 var teamNum: Int? = 0,
                 var title: String? = "",
                 var teams: Map<String, Any>? = null,
                 var users: Map<String, Boolean>? = null
)