package com.bback.bbackdo.dto

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
        const val TEAM1 = "안동소주 팀"
        const val TEAM2 = "문배주 팀"
        const val TEAM3 = "진도홍주 팀"
        const val TEAM4 = "이강주 팀"
    }
}