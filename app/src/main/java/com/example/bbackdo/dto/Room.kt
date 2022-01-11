package com.example.bbackdo.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Room (var manager: String? = "",
                 var memberNum: Int? = 0,
                 var password: String? = "",
                 var penalty: List<Int>? = listOf(0, 0, 0, 0, 0),
                 var rid: String? = "",
                 var teamNum: Int? = 0,
                 var title: String? = "",
                 var teams: @RawValue Map<String, Any>? = null,
                 var users: Map<String, Boolean>? = null,
                 var state: String? = STATE_WAIT
):
    Parcelable {
            companion object{
                const val STATE_WAIT = "WAIT"
                const val STATE_READY = "READY"
                const val STATE_START = "START"
            }
        }