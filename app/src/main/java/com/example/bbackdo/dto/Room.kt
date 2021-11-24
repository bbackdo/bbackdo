package com.example.bbackdo.dto


data class Room (var manager: String? = "",
                 var memberNum: Int? = 0,
                 var password: String? = "",
                 var penalty: List<Int>? = null,
                 var rid: String? = "",
                 var teamNum: Int? = 0,
                 var title: String? = "",
                 var teams: Map<String, Any>? = null
)