package com.bback.bbackdo.dto

data class User(var nickname: String = "",
                var win: Int = 0,
                var lose: Int = 0,
                var readyState: Boolean = false,
                var teams: Map<String, Any>? = null,
                var userId: Int= 0
)

//사람 - id, 닉네임, 승패정보, 들어간방, 레디
// nickname, win, lose, readyState, rooms