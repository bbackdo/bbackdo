package com.example.bbackdo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bbackdo.databinding.CellMemberBinding
import com.example.bbackdo.databinding.LayoutTeamBinding
import com.example.bbackdo.dto.Team
import com.example.bbackdo.dto.User
import com.example.bbackdo.lib.Authentication
import com.example.bbackdo.lib.Database
import com.example.bbackdo.lib.Util
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.getValue
import splitties.activities.start
import splitties.bundle.putExtras

class TeamAdapter(
    private val context: Context,
    private var listData: ArrayList<Team>
) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>() {


    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.ViewHolder {
        val view =
            LayoutTeamBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamAdapter.ViewHolder, position: Int) {
        val team: Team = listData[position]
        holder.binding(team)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    inner class ViewHolder(private val bind: LayoutTeamBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(team: Team) {
            with(bind) {


                teamName.setText(team.tid)
                val dataList = arrayListOf<User>()
                val adapter = EachTeamAdapter(context, dataList)


                teamRcv.adapter = adapter

            }


        }

    }

}

class EachTeamAdapter(
    private val context: Context,
    private var listData: ArrayList<User>
) :
    RecyclerView.Adapter<EachTeamAdapter.ViewHolder>() {


    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EachTeamAdapter.ViewHolder {
        val view =
            CellMemberBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EachTeamAdapter.ViewHolder, position: Int) {
        val user: User = listData[position]
        holder.binding(user)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    inner class ViewHolder(private val bind: CellMemberBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun binding(user: User) {
            with(bind) {
                memberIdText.text = user.nickname
            }


        }

    }
}



