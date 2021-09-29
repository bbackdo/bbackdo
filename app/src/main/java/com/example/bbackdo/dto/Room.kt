package com.example.bbackdo.dto

data class Room (var title: String ="",
                 var password: String = "",
                 var manager: String ="",
                 var memberNum: Int = 0,
                 var teamNum: Int = 0,
                 var teams: Map<String, Any>? = null,
                 var penalty: Map<String, Any>? = null
)