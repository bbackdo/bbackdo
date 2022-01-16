package com.example.bbackdo.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Team(
    var tid: String? = "",
    var result: Boolean? = false,
    var members: @RawValue Map<String, Any>? = null,
    var name: String? = TEAM1,
    var rid: String? = ""
) : Parcelable {
    companion object {
        const val TEAM1 = "소주팀"
        const val TEAM2 = "맥주팀"
        const val TEAM3 = "양주팀"
        const val TEAM4 = "칵테일팀"
    }
}