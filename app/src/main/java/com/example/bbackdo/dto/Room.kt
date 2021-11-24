package com.example.bbackdo.dto


data class Room (
    var rid: String? = null,
                 var title: String? ="",
                 var password: String? = "",
                 var memberNum: Int? = 0,
                 var manager: String? ="",
                 var teamNum: Int? = 0,
                 var users: Map<String, String>? = null,
                 var team: Map<String, Int>? = null,
                 var penalty: List<String>? = null
)