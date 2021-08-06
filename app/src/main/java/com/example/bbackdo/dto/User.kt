package com.example.bbackdo.dto

data class User(var nickname: String = "",
                var win: Int = 0,
                var lose: Int = 0,
                var readyState: Boolean = false,
                var rooms: Map<String, Any>? = null)

//사람 - id, 닉네임, 승패정보, 들어간방, 레디